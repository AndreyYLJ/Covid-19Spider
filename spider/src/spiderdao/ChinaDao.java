package spiderdao;
import spiderentity.Province;
import util.DBUtil;

public class ChinaDao {
    public boolean insertProvince(Province province) {
        String sql = "insert into china values(?,?,?,?,?,?,?)";
        Object[] params = {province.getProvinceName(),province.getProvinceShortName(),province.getConfirmedCount(),province.getCurrentConfirmedCount(),province.getSuspectedCount(),province.getCuredCount(),province.getDeadCount()};
        return DBUtil.executeUpdate(sql, params);
    }

    public boolean clearAllProvince()
    {
        String sql="delete from china";
        Object[] params={};
        return DBUtil.executeUpdate(sql, params);
    }
}
