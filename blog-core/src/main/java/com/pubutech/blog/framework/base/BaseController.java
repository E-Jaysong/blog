package com.pubutech.blog.framework.base;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    /*
    public class User implements Serializable{
        String id;
        String name;
        //get..set....
    }

    public class Addr implements Serializable{

        String id;

        String name;
        //set..get...
    }

    <form action="/test/test" method="post">
        <input type="text" name="user.id" value="huo_user_id">
        <input type="text" name="user.name" value="huo_user_name">
        <input type="text" name="addr.id" value="huo_addr_id">
        <input type="text" name="addr.name" value="huo_addr_name">
        <input type="submit" value="提交">
    </form>

    @RequestMapping("/test")
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request,@ModelAttribute("user") User user,@ModelAttribute("addr") Addr addr){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("user", user);
        map.put("addr", addr);
        return map;
    }

     // 绑定变量名字和属性，参数封装进类
    @InitBinder("user")
    public void initBinderUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user.");
    }
    // 绑定变量名字和属性，参数封装进类
    @InitBinder("addr")
    public void initBinderAddr(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("addr.");
    }

     */


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Double.class, new DoubleEditor());
        binder.registerCustomEditor(Integer.class, new IntegerEditor());
    }

    public class DoubleEditor extends PropertiesEditor {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.equals("")) {
                text = "0";
            }
            setValue(Double.parseDouble(text));
        }

        @Override
        public String getAsText() {
            return getValue().toString();
        }
    }

    public class IntegerEditor extends PropertiesEditor {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.equals("")) {
                text = "0";
            }
            setValue(Integer.parseInt(text));
        }

        @Override
        public String getAsText() {
            return getValue().toString();
        }
    }

}
