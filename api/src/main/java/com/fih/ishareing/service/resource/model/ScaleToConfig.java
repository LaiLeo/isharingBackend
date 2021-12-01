/**
 * 
 */
/**
 * @author YujinHsu
 *
 */

package com.fih.ishareing.service.resource.model;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class ScaleToConfig implements Serializable {
	
	private Integer width;
	private Integer height;
	
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

}
