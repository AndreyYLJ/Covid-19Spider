package service;

import spiderentity.City;
import spiderentity.Country;
import spiderentity.Province;
import spiderentity.ProvinceAndCity;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Spider spider = new Spider();
        String url = "https://ncov.dxy.cn/ncovh5/view/pneumonia";
        String httpResult = null;
        try {
            httpResult = spider.httpRequset(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            if (httpResult != null) {
                {   //处理世界数据
                    List<Country> countries = spider.GetWorldResult(httpResult);
                    CountryService countryService = new CountryService();
                    // System.out.println(countryService.addCountry(countries));//重新插入
                    System.out.println("更新世界数据" + countryService.updateAllCountry(countries));//更新
                }

                ProvinceAndCity provinceAndCity = spider.GetChinaResult(httpResult);//处理省和市的数据
                List<Province> provinces = provinceAndCity.getProvinces();
                List<City> cities = provinceAndCity.getCities();

                ProvinceService provinceService = new ProvinceService();
                System.out.println("更新省数据" + provinceService.updateAllProvince(provinces));

                CityService cityService = new CityService();
                System.out.println("更新城市" + cityService.updateAllCity(cities));

                try {
                    Thread.sleep(1000*60*60*2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("q");
            }
        }
    }
}
