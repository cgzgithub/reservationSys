package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyMenu;
import com.ust.shbay.manager.entity.SyMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SyMenuMapper {
    long countByExample(SyMenuExample example);

    int deleteByExample(SyMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SyMenu record);

    int insertSelective(SyMenu record);

    List<SyMenu> selectByExample(SyMenuExample example);

    SyMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SyMenu record, @Param("example") SyMenuExample example);

    int updateByExample(@Param("record") SyMenu record, @Param("example") SyMenuExample example);

    int updateByPrimaryKeySelective(SyMenu record);

    int updateByPrimaryKey(SyMenu record);

    @Select("SELECT DISTINCT sm.* \n" +
            "FROM sy_account sa INNER JOIN sy_account_role sar ON (sa.id = #{accountId} AND sa.del_flag = 1 AND sar.del_flag = 1 AND sa.id = sar.account_id) \n" +
            "INNER JOIN sy_role_menu srm ON (srm.del_flag = 1 AND srm.role_id = sar.role_id)\n" +
            "INNER JOIN sy_menu sm ON (sm.del_flag = 1 AND srm.menu_id = sm.id)\n" +
            "ORDER BY sm.parent_id ASC, sm.m_order ASC")
    List<SyMenu> selectAccountMenu(@Param("accountId") String accountId);

    @Select("select id, name, path, parent_id from sy_menu where del_flag = 1 order by parent_id asc, m_order asc")
    List<SyMenu> selectAll();
}