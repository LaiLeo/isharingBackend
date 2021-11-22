package com.fih.ishareing.service.token;

import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.auth.model.IeqAccessTokenVO;
import com.fih.ishareing.service.auth.model.RefreshTokenRespVO;

import org.springframework.security.core.Authentication;

public interface TokenService {

	AuthTokenRespVO generateToken(Authentication authentication);

	RefreshTokenRespVO refreshToken(Authentication authentication);

    Authentication readAuthentication(String accessToken);

    Authentication readAuthenticationForRefreshToken(String accessToken);

    void removeToken(String account, String accessToken);

    void removeTokenUseAccount(String account);

    void removeAccessTokenUseRefreshToken(String refreshToken, String username);
}