package service;

import spiderdao.WorldDao;
import spiderentity.Country;

import java.util.Iterator;
import java.util.List;

public class CountryService {
    public boolean addCountry(List<Country> countries)//增加国家信息
    {
        WorldDao worldDao=new WorldDao();
        Iterator<Country> iterator =countries.iterator();
        while (iterator.hasNext())
            if (!worldDao.insertCountry(iterator.next()))
                return false;
            return  true;
    }
    public boolean updateAllCountry(List<Country> countries)
    {
        WorldDao worldDao= new WorldDao();
        if(!worldDao.clearAllCountry())
            return false;
        return addCountry(countries);
    }

}
