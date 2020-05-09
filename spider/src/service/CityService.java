package service;

import spiderdao.CityDao;
import spiderentity.City;

import java.util.Iterator;
import java.util.List;

public class CityService {
    public boolean addCity(List<City> cities)
    {
        CityDao cityDao = new CityDao();
        Iterator<City> iterator =cities.iterator();
        while (iterator.hasNext())
            if (!cityDao.insertCity(iterator.next()))
                return false;
        return  true;
    }
    public boolean updateAllCity(List<City> cities)
    {
        CityDao cityDao=new CityDao();
        if(!cityDao.clearAllCities())
            return false;
        return addCity(cities);
    }
}
