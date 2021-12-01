package com.fih.ishareing.service.reset;

import com.fih.ishareing.service.reset.model.ResetPasswordReqVO;
import com.fih.ishareing.service.reset.model.ResetPasswordRespVO;
import com.fih.ishareing.service.reset.model.ResetPermissionReqVO;
import com.fih.ishareing.service.reset.model.ResetPermissionRespVO;

public interface ResetService {

	ResetPermissionRespVO getResetCode(ResetPermissionReqVO resetpermission);
	
	ResetPasswordRespVO updatePassword(ResetPasswordReqVO resetpassword);
}