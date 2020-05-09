package service;

import spiderdao.ChinaDao;
import spiderentity.Province;

import java.util.Iterator;
import java.util.List;

public class ProvinceService {
    public boolean addProvince(List<Province> provinces)//增加国家信息
    {
        ChinaDao chinaDao=new ChinaDao();
        Iterator<Province> iterator =provinces.iterator();
        while (iterator.hasNext())
            if (!chinaDao.insertProvince(iterator.next()))
                return false;
        return  true;
    }
    public boolean updateAllProvince(List<Province> provinces)
    {
        ChinaDao chinaDao=new ChinaDao();
        if(!chinaDao.clearAllProvince())
            return false;
        return addProvince(provinces);
    }

}
