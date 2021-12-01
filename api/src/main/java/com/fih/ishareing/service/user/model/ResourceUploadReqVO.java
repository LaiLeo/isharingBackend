/**
 * 
 */
/**
 * @author YujinHsu
 *
 */

package com.fih.ishareing.service.user.model;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class ResourceUploadReqVO implements Serializable {
	
	private MultipartFile image;

	private Integer displaySort;

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Integer getDisplaySort() {
		return displaySort;
	}

	public void setDisplaySort(Integer displaySort) {
		this.displaySort = displaySort;
	}
}
