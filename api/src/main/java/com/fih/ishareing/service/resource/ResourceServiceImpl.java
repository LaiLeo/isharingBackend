package com.fih.ishareing.service.resource;

import com.fih.ishareing.errorHandling.exceptions.ImageScaleToConfigInValidException;
import com.fih.ishareing.errorHandling.exceptions.ImageTypeInValidException;
import com.fih.ishareing.errorHandling.exceptions.InternalErrorException;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.resource.model.ScaleToConfig;
import com.fih.ishareing.utils.checksum.Md5Checksum;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.imgscalr.Scalr;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

@Service
public class ResourceServiceImpl extends AbstractService implements ResourceService {
    private static Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Value("${file.server.base.dir}")
    private String strFileServerBaseDir;
    
    private String strFileServerTmpDir = "/tmp/twm/";
    private List<String> listStrImageType = Arrays.asList("jpg", "jpeg", "png", "gif");
    
	@Autowired
	private Md5Checksum Md5ChecksumObj;
	
	/*
	 *  ResourceService.imageScaleTo(MultipartFile image, String targetpath, Boolean usemd5name, ScaleToConfig config)
	 *  image : Upload pic file.
	 *  targetpath : upload target path, please use full path name. Ex, user/myphoto.jpg .
	 *  usemd5name : true(filename used md5 instead), false(use original name).
	 *  config : config scale to width & height. If is null, save pic as original size.
	 *  
	 *  Exception: 
	 *  	ParameterException
	 *  	ImageTypeInValidException
	 *  	ImageScaleToConfigInValidException
	 *  	InternalErrorException
	 */
	@Override
	public String imageScaleTo(MultipartFile image, String targetpath, Boolean usemd5name, ScaleToConfig config) {
		// TODO Auto-generated method stub
		String strOSLabel = System.getProperty("os.name");
		String strOrigFileName = (image != null && !image.isEmpty())?image.getOriginalFilename().replaceAll("'", "").replaceAll("\\\\", "").replaceAll("./", "").replaceAll("/", "").replaceAll("\\.\\.", ""):"";
//		String strFilePath = (strOSLabel.indexOf("Win") < 0)?
//				String.format("%s%s", strFileServerBaseDir, targetpath):
//				String.format("%s%s", strFileServerBaseDir, targetpath);
		String strFilePath = targetpath;
		JSONObject JOPathInfo = pathInfo(strFilePath);
		String strTargetFilePath = JOPathInfo.get("Path").toString();
		String strTargetFileName = JOPathInfo.get("FullFileName").toString();
		String strTargetFileExtName = JOPathInfo.get("FileExtName").toString().toLowerCase();
		String strTargetFilePathName = String.format("%s%s%s", strTargetFilePath, strTargetFilePath.isEmpty()?"":"/", strTargetFileName);
		String strBasTargetFilePath = String.format("%s%s", strFileServerBaseDir, strTargetFilePath);
		String strBasTargetFilePathName = String.format("%s%s%s", strBasTargetFilePath.endsWith("/")?strBasTargetFilePath.substring(0, strBasTargetFilePath.length()-1):strBasTargetFilePath, 
				strBasTargetFilePath.isEmpty()?"":"/", strTargetFileName);
		long lFileLength = 0;
		String strFileCheckSum = "";
		
		// Validate Parameter
		if(image == null || targetpath == null || targetpath.isEmpty())
			throw new ParameterException();
		
		// Validate image type
		if(!listStrImageType.contains(strTargetFileExtName))
			throw new ImageTypeInValidException();
		
		// Validate scale to config
		if(config != null && (config.getHeight() == 0 || config.getWidth() == 0))
			throw new ImageScaleToConfigInValidException();

		// Check if upload tmp dir exists.
		File fTmpFilePath = new File(strFileServerTmpDir);
		try {
			Files.createDirectories(Paths.get(strFileServerTmpDir));
//			if(!fTmpFilePath.exists() && strOSLabel.indexOf("Win") < 0)
//				Files.createDirectories(Paths.get(strFileServerTmpDir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalErrorException(String.format("Initial upload temp direcotry failed. [Dir: %s]", strFileServerTmpDir));
		}					
		
		// Check if resource dir exists.
//		File fFilePath = new File(strTargetFilePath);
		try {
			if(strBasTargetFilePath != null && !strBasTargetFilePath.isEmpty())
				Files.createDirectories(Paths.get(strBasTargetFilePath));
//			if(!fFilePath.exists() && strOSLabel.indexOf("Win") < 0)
//				Files.createDirectories(Paths.get(strTargetFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalErrorException(String.format("Initial resource direcotry failed. [Dir: %s]", strBasTargetFilePath));
		}					
		
		// Check upload file exists
		File fFile = null;
		if(image != null && !image.isEmpty())
		{
			try {
				InputStream filestream = image.getInputStream();
				String strTmpFilePath = (strOSLabel.indexOf("Win") >= 0)?strOrigFileName:String.format("%s%s", strFileServerTmpDir, strOrigFileName);
				strTmpFilePath = String.format("%s%s", strFileServerTmpDir, strOrigFileName);
				File targetFile = new File(strTmpFilePath);

			    OutputStream outStream = new FileOutputStream(targetFile);
			    byte[] buffer = new byte[1024 * 1024];
			    int bytesRead;
			    while ((bytesRead = filestream.read(buffer)) != -1) {
			        outStream.write(buffer, 0, bytesRead);
			    }
			    IOUtils.closeQuietly(filestream);
			    IOUtils.closeQuietly(outStream);

				fFile = targetFile;
				strFileCheckSum = Md5ChecksumObj.digestHex(fFile.getPath());
				lFileLength = fFile.length();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new InternalErrorException(String.format("Upload image to temp direcotry failed. [Exception: %s]", e.getMessage()));
//		    	logger.info(String.format("Check upload file exists. [Exception: %s]", e.getMessage()));
			}
		}
//    	String strTargetFileName = String.format("%s.%s", strFileCheckSum, strFileExtendName);
//    	logger.info(String.format("Upload File Path: %s, File Size: %d, File CheckSum: %s", 
//    			(fFile != null)?fFile.getPath():"", 
//    			(fFile != null)?fFile.length():0, 
//    			(fFile != null)?strFileCheckSum:""));

		// CASE I. use original size
		if(config == null)
		{
			// Copy file to download path
	    	if(fFile != null)
	    	{
	    		strBasTargetFilePathName = (usemd5name)?
	    				String.format("%s/%s.%s", strBasTargetFilePath, strFileCheckSum, strTargetFileExtName):
	    				strBasTargetFilePathName;
	    	    strTargetFilePathName = (usemd5name)?
	    	    		String.format("%s/%s.%s", strTargetFilePath, strFileCheckSum, strTargetFileExtName):
	    	    		strTargetFilePathName;
				File fDestFileDir = new File(strBasTargetFilePath);
				File fDestFilePath = new File(strBasTargetFilePathName);
				try {
					if(!fDestFileDir.exists())
						Files.createDirectories(Paths.get(strBasTargetFilePath));
					Files.deleteIfExists(Paths.get(strBasTargetFilePathName));
					FileSystemUtils.copyRecursively(fFile, fDestFilePath);
//			    	logger.info(String.format("Copy upload file to dir success. [Src Path: %s][Dest Path: %s]", fFile.getPath(), fDestFilePath.getPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new InternalErrorException(String.format("Store image to resource direcotry failed. [Exception: %s]", e.getMessage()));
//			    	logger.info(String.format("Copy upload file to dir failed. [Src Path: %s][Dest Path: %s][Exception: %s]", fFile.getPath(), fDestFilePath.getPath(), e.getMessage()));
//			    	throw new InternalErrorException(e.getMessage());
				}
	    	}
		}
		else
		{
			// Copy file to download path
	    	if(image != null)
	    	{
				BufferedImage bufferedNewImage = null;
				try {
					BufferedImage bufferedImage = InputImage(image);
					
					bufferedNewImage = Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, config.getWidth(), config.getHeight(), Scalr.OP_ANTIALIAS);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					throw new InternalErrorException(String.format("Resize image parameter failed. [Exception: %s]", e1.getMessage()));
				} catch (ImagingOpException e1) {
					// TODO Auto-generated catch block
					throw new InternalErrorException(String.format("Resize image failed. [Exception: %s]", e1.getMessage()));
				}
			
				File fDestFileDir = new File(strBasTargetFilePath);
				File fDestFilePath = new File(strBasTargetFilePathName);
				try {
					if(!fDestFileDir.exists())
						Files.createDirectories(Paths.get(strBasTargetFilePath));
					Files.deleteIfExists(Paths.get(strBasTargetFilePathName));
					ImageIO.write(bufferedNewImage, strTargetFileExtName, fDestFilePath);
					if(usemd5name)
					{
						strFileCheckSum = Md5ChecksumObj.digestHex(fDestFilePath.getPath());
			    		String strNewBasTargetFilePathName = String.format("%s/%s.%s", strBasTargetFilePath, strFileCheckSum, strTargetFileExtName);
			    		String strNewTargetFilePathName = String.format("%s/%s.%s", strTargetFilePath, strFileCheckSum, strTargetFileExtName);
			    		File fNewDestFilePath = new File(strNewBasTargetFilePathName);
						FileSystemUtils.copyRecursively(fDestFilePath, fNewDestFilePath);
						Files.deleteIfExists(Paths.get(strBasTargetFilePathName));
						strBasTargetFilePathName = strNewBasTargetFilePathName;
						strTargetFilePathName = strNewTargetFilePathName;
					}
//					FileSystemUtils.copyRecursively(fFile, fDestFilePath);
//			    	logger.info(String.format("Copy upload file to dir success. [Src Path: %s][Dest Path: %s]", fFile.getPath(), fDestFilePath.getPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new InternalErrorException(String.format("Store image to resource direcotry failed. [Exception: %s]", e.getMessage()));
//			    	logger.info(String.format("Copy upload file to dir failed. [Src Path: %s][Dest Path: %s][Exception: %s]", fFile.getPath(), fDestFilePath.getPath(), e.getMessage()));
//			    	throw new InternalErrorException(e.getMessage());
				}
	    	}
		}
		
		return strTargetFilePathName;
	}
	
	private JSONObject pathInfo(String strPath)
	{
		Path p = Paths.get(strPath);
		JSONObject JOPathInfo = new JSONObject() {{
			put("Path", p.getParent().toString());
			put("FullFileName", p.getFileName().toString());
			put("FileExtName", "");
		}};
		String[] fileExtList = p.getFileName().toString().split("\\.");
		if (fileExtList.length == 2) {
			JOPathInfo.put("FileExtName", fileExtList[1]);
		}
		
		return JOPathInfo;
	}
	
	private BufferedImage InputImage(MultipartFile file) {  
		BufferedImage srcImage = null;  
	    try {  
	    	FileInputStream in = (FileInputStream) file.getInputStream();
	        srcImage = javax.imageio.ImageIO.read(in);  
	    } catch (IOException e) {
	        System.out.println(e.getMessage());  
	    }
	    
	    return srcImage;  
	}

	@Override
	public void imageRemove(String image) {
		// TODO Auto-generated method stub
		String strBasTargetFilePath = String.format("%s%s", strFileServerBaseDir, image);
		
		try {
			Files.deleteIfExists(Paths.get(strBasTargetFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}