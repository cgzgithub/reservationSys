package com.ust.shbay.service.session;

import com.ust.shbay.common.UUID;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.enums.LoginChannel;
import com.ust.shbay.manager.dao.SyAccountSessionMapper;
import com.ust.shbay.manager.entity.SyAccount;
import com.ust.shbay.manager.entity.SyAccountSession;
import com.ust.shbay.manager.entity.SyAccountSessionExample;
import com.ust.shbay.service.account.AccountService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Value("${sessionExpire}")
    private String sessionExpire;

    @Autowired
    private SyAccountSessionMapper syAccountSessionMapper;

    @Autowired
    private AccountService accountService;

    @Override
    public String createSession(SyAccount syAccount, LoginChannel channel) {
        String accountId = syAccount.getId();
        // 删除旧记录
        deleteSession(syAccount, channel);

        SyAccountSession entity = new SyAccountSession();
        entity.setAccountId(accountId);
        entity.setAccount(syAccount.getAccount());
        String uuid = UUID.randomUUID();
        entity.setSessionCode(uuid);
        Integer hour = Integer.parseInt(sessionExpire);
        DateTime dateTime = DateTime.now().plusHours(hour);
        entity.setExpireTime(dateTime.toDate());
        entity.setChannel(channel.name());
        syAccountSessionMapper.insertSelective(entity);

        return uuid;
    }

    @Override
    public void deleteSession(SyAccount syAccount, LoginChannel channel) {
        String accountId = syAccount.getId();
        // 删除旧记录
        SyAccountSessionExample example = new SyAccountSessionExample();
        SyAccountSessionExample.Criteria criteria = example.createCriteria();
        criteria.andAccountIdEqualTo(accountId);
        criteria.andChannelEqualTo(channel.name());
        List<SyAccountSession> syAccountSessionList = syAccountSessionMapper.selectByExample(example);

        for (SyAccountSession syAccountSession : syAccountSessionList) {
            syAccountSessionMapper.deleteByPrimaryKey(syAccountSession.getId());
        }
    }

    @Override
    public SyAccount getAccountBySession(String sessionCode, LoginChannel channel) {

        SyAccountSession session = null;

        SyAccountSessionExample example = new SyAccountSessionExample();
        SyAccountSessionExample.Criteria criteria = example.createCriteria();

        criteria.andSessionCodeEqualTo(sessionCode);
//        criteria.andChannelEqualTo(channel.name());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccountSession> accountSessionList = syAccountSessionMapper.selectByExample(example);

        if (accountSessionList.size() > 0) {
            session = accountSessionList.get(0);
        }

        if (session == null) {
            return null;
        }
        return accountService.getAccount(session.getAccount());
    }
}
