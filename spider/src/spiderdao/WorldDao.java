package spiderdao;

import spiderentity.Country;
import util.DBUtil;

public class WorldDao {
    DBUtil dbUtil= new DBUtil();
    public boolean insertCountry(Country country) {
        String sql = "insert into world values(?,?,?,?,?,?)";
        Object[] params = {country.getCountryName(),country.getContinent(),country.getCurrentConfirmedCount(),country.getConfirmedCount(),country.getCuredCount(),country.getDeadCount(),};
        return DBUtil.executeUpdate(sql, params);
    }

    public boolean clearAllCountry()
    {
        String sql="delete from world";
        Object[] params={};
        return DBUtil.executeUpdate(sql, params);
    }

}
