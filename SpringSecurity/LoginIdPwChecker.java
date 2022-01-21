import com.entropykorea.biztalkmng.config.manage.SenderProfileManager;
import com.entropykorea.biztalkmng.service.mapper.BizTalkMngMapper;
import com.entropykorea.biztalkmng.service.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginIdPwChecker implements UserDetailsService {
    @Autowired
    private BizTalkMngMapper mapper;

    @Autowired
    private SenderProfileManager senderProfileManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA512PasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String insertedId) throws UsernameNotFoundException {
        LoginVo vo = new LoginVo();
        vo.setId(insertedId);
        List<LoginVo> list = new ArrayList<>();
        try {
            list = mapper.getLoginInfo(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null || list.isEmpty() || list.size() > 1) {
            return null;
        }

        LoginVo dbUser = list.get(0);
        senderProfileManager.setSenderProfileKey(insertedId, dbUser.getSender_profile_key());

        return User.builder()
                .username(dbUser.getId())
                .password(dbUser.getPw())
                .roles(dbUser.getRole_nm())
                .build();
    }
}
