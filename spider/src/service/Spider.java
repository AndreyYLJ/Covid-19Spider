package service;

import spiderdao.ChinaDao;
import spiderdao.CityDao;
import spiderentity.City;
import spiderentity.Country;
import spiderentity.Province;
import spiderentity.ProvinceAndCity;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider {
    public String httpRequset(String requesturl) throws IOException {
        StringBuffer buffer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL(requesturl);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestMethod("GET");
            inputStream = httpsURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public List<Country> GetWorldResult(String str) {
        List<Country> countries= new ArrayList<>();
        String[] countryResult = str.split("\"countryType\":2,|window.getTimelineService2 ");//获取各个国家的信息
        for (int i = 1; i < countryResult.length - 1; i++) {//每个国家的数据 头尾需要去掉
            //细分数据
            Country country = new Country();
            String regex = "\"continents\":\"|\",\"provinceId\":\"[0-9]*\",\"provinceName\":\"|\",\"provinceShortName\":\"\",\"cityName\":\"\",\"currentConfirmedCount\":|,\"confirmedCount\":|,\"suspectedCount\":[0-9]+,\"curedCount\":|,\"deadCount\":|,\"deadRate\":\"[0-9]+.[0-9]+\",\"|,\"confirmedCountRank\":[0-9]+|,\"deadCountRank\":[0-9]+";
            String[] countryInformation = countryResult[i].split(regex);
            try {
                country.setContinent(countryInformation[1]);
                country.setCountryName(countryInformation[2]);
                country.setCurrentConfirmedCount(Integer.parseInt(countryInformation[3]));
                country.setConfirmedCount(Integer.parseInt(countryInformation[4]));
                if (countryInformation[5].equals("")) {
                    country.setCuredCount(Integer.parseInt(countryInformation[6]));
                    country.setDeadCount(Integer.parseInt(countryInformation[7]));
                } else {
                    country.setCuredCount(Integer.parseInt(countryInformation[5]));
                    country.setDeadCount(Integer.parseInt(countryInformation[6]));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            countries.add(country);
        }
        return countries;
    }

    public ProvinceAndCity GetChinaResult(String string) {
        ProvinceAndCity provinceAndCity = new ProvinceAndCity();
        String[] ChinaResult=string.split("window.getAreaStat = \\[|\\{\"provinceName\":\"");
        for(int i=2;i<ChinaResult.length;i++)
        {
            //System.out.println(ChinaResult[i]);
            String regex="\"cities\":";
            String[] province=ChinaResult[i].split(regex);
            //0表示省  1表示各个地级市
            //System.out.println(province[0]);
            //System.out.println(province[1]);
            Province province1=new Province();
            String pRegex="\",\"provinceShortName\":\"|\",\"currentConfirmedCount\":|,\"confirmedCount\":|,\"suspectedCount\":|,\"curedCount\":|,\"deadCount\":|,\"comment\"";
            String[] provinceInformation=province[0].split(pRegex);
            province1.setProvinceName(provinceInformation[0]);
            province1.setProvinceShortName(provinceInformation[1]);
            province1.setConfirmedCount(Integer.parseInt(provinceInformation[2]));
            province1.setCurrentConfirmedCount(Integer.parseInt(provinceInformation[3]));
            province1.setSuspectedCount(Integer.parseInt(provinceInformation[4]));
            province1.setCuredCount(Integer.parseInt(provinceInformation[5]));
            province1.setDeadCount(Integer.parseInt(provinceInformation[6]));
            provinceAndCity.getProvinces().add(province1);
           // ChinaDao chinaDao = new ChinaDao();
            //chinaDao.insertProvince(province1);//插入省

            //处理地级市
            String cRegex="\\{\"cityName\":\"";
            String[] cityInformation=province[1].split(cRegex);
            for(int j=1;j<cityInformation.length;j++)//每个city的信息
            {
                City city1= new City();
                String ccRegex="\",\"currentConfirmedCount\":|,\"confirmedCount\":|,\"suspectedCount\":|,\"curedCount\":|,\"deadCount\":|,\"locationId\":";
                String[] city=cityInformation[j].split(ccRegex);
                city1.setProvinceName(provinceInformation[0]);
                city1.setCityName(city[0]);
                city1.setCurrentConfirmedCount(Integer.parseInt(city[1]));
                city1.setConfirmedCount(Integer.parseInt(city[2]));
                city1.setSuspectedCount(Integer.parseInt(city[3]));
                city1.setCuredCount(Integer.parseInt(city[4]));
                city1.setDeadCount(Integer.parseInt(city[5]));
                provinceAndCity.getCities().add(city1);
                //CityDao cityDao = new CityDao();
                //cityDao.insertProvince(city1);//插入市
            }
        }
        return provinceAndCity;
    }
}
