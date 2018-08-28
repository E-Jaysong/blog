package com.pubutech.blog.framework.datasource;
import com.pubutech.blog.framework.holder.DatabaseContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource  extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getCustomerType();
    }

}
