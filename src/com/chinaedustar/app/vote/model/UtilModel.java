package com.chinaedustar.app.vote.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UtilModel implements TemplateHashModelEx, TemplateModelObject {

    /** 所有方法的集合 */
    private Map<String, Object> methods = new HashMap<String, Object>();
    public UtilModel() {
        methods.put("getDateByLong", new GetDateByLong());
    }
    
    
    /**
     * 时间的转换函数
     * 需要一个或 两个参数，第一个参数是LongTime,如果传入第2个参数，则第2个参数是字符串格式化参数例如 yyyy-MM-dd HH:mm:ss等 
     * 如果只有一个参数，则返回  Date 类型的
     * 如果两个参数，则返回格式化后则事件字符串
     * @author dell
     *
     */
    public class GetDateByLong  implements TemplateMethodModelEx {

		@Override
        public Object exec(List args) throws TemplateModelException {
            if (args == null || args.size() == 0){
                throw new TemplateModelException(" getDateByLong 至少需要1个参数");
            }
            String strValue = args.get(0).toString();
            long dateTime = Long.valueOf(strValue);
            java.util.Date dt = new Date(dateTime);
            if(args.size() == 1){
                return dt;
            }
            String strFormat = args.get(1).toString();
            SimpleDateFormat sdf= new SimpleDateFormat(strFormat);
            String sDateTime = sdf.format(dt);
            return sDateTime;
        }
        
    }
    
    
    @Override
    public boolean isEmpty() throws TemplateModelException {
        return false;
    }

    @Override
    public String getVariableName() {
        return "Util";
    }

    @Override
    public TemplateCollectionModel keys() throws TemplateModelException {
        return (TemplateCollectionModel)Environment.getCurrentEnvironment().getObjectWrapper().wrap(this.methods.keySet());
    }

    @Override
    public int size() throws TemplateModelException {
        return this.methods.size();
    }


    @Override
    public TemplateCollectionModel values() throws TemplateModelException {
        throw new TemplateModelException("Unsupport values() method.");
    }
    
	public void setMethods(Map m) {
        methods.putAll(m);
    }

    @Override
    public TemplateModel get(String name) throws TemplateModelException {
        Object val = this.methods.get(name);
        if (val == null) return TemplateModel.NOTHING;
        if (val instanceof TemplateModel) return (TemplateModel)val;
        return Environment.getCurrentEnvironment().getObjectWrapper().wrap(val);
    }
}
