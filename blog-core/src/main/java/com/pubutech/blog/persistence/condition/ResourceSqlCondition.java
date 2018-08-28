package com.pubutech.blog.persistence.condition;

import com.pubutech.blog.business.entity.Resources;
import com.pubutech.blog.framework.base.BaseSqlConditionBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceSqlCondition extends BaseSqlConditionBean {
    private Resources resources;
}
