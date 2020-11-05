
package com.ust.shbay.manager.api.controller.dto.account;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginChannel extends BaseToken {

    private String loginChannel;

}
