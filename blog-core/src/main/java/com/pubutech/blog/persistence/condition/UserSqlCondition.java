package com.pubutech.blog.persistence.condition;

import com.pubutech.blog.business.entity.User;
import com.pubutech.blog.framework.base.BaseSqlConditionBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSqlCondition extends BaseSqlConditionBean {

    private User user;

}
