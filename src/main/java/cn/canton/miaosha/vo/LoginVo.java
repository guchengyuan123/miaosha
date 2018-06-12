package cn.canton.miaosha.vo;

import javax.validation.constraints.NotNull;

import cn.canton.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    @Override
    public String toString() {
        return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
    }
}
