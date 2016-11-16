package com.coolweather.app.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {


	/**
	 * 解析和处理服务器返回的省级数据
	 */	
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = null;
			allProvinces = response.split(",");
			allProvinces[0] = allProvinces[0].replace("{", "");
			allProvinces[allProvinces.length-1] = allProvinces[allProvinces.length-1].replace("}", "");	
			for(int i = 0;i<allProvinces.length;i++){
			}
			if(allProvinces != null && allProvinces.length > 0){
				for(String p : allProvinces){
					String[] array = p.split(":");
					array[0] = array[0].replace("\"", "");
					array[1] = array[1].replace("\"", "");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = null;
			if(response.indexOf(",") == -1){
				allCities = response.split(":");
				if(allCities != null && allCities.length > 0){
				allCities[0] = allCities[0].replace("{", "");
				allCities[0] = allCities[0].replace("\"", "");       
				allCities[1] = allCities[1].replace("}", "");
				allCities[1] = allCities[1].replace("\"", "");
				City city = new City();
				city.setCityCode(allCities[0]);
				city.setCityName(allCities[1]);
				city.setProvinceId(provinceId);
				coolWeatherDB.saveCity(city);
				Log.d("city", ""+allCities[0]);
				Log.d("city", ""+allCities[1]);
				}
				return true;
			}else{
				allCities = response.split(",");
				allCities[0] = allCities[0].replace("{", "");
				allCities[allCities.length-1] = allCities[allCities.length-1].replace("}", "");
				if(allCities != null && allCities.length > 0){
					for(String c : allCities){
						String[] array = c.split(":");
						array[0] = array[0].replace("\"", "");
						array[1] = array[1].replace("\"", "");
						City city = new City();
						city.setCityCode(array[0]);
						city.setCityName(array[1]);
						city.setProvinceId(provinceId);
						coolWeatherDB.saveCity(city);
					}
					return true;
				}
			}
			
		}
		return false;	
	}
	
	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
		//Log.d("county", ""+response);
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = null;
			if(response.indexOf(",") == -1){
				allCounties = response.split(":");
				if(allCounties != null && allCounties.length > 0){
				allCounties[0] = allCounties[0].replace("{", "");
				allCounties[0] = allCounties[0].replace("\"", "");       
				allCounties[1] = allCounties[1].replace("}", "");
				allCounties[1] = allCounties[1].replace("\"", "");
				County county = new County();
				county.setCountyCode(allCounties[0]);
				county.setCountyName(allCounties[1]);
				county.setCityId(cityId);
				coolWeatherDB.saveCounty(county);
				}
				return true;
			}else{
			allCounties = response.split(",");
			allCounties[0] = allCounties[0].replace("{","");
			allCounties[allCounties.length-1] = allCounties[allCounties.length-1].replace("}", "");			 
			if(allCounties != null && allCounties.length > 0){
				for(String c : allCounties){
					String[] array = c.split(":");
					array[0] = array[0].replace("\"", "");
					array[1] = array[1].replace("\"", "");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		  }
		}
		return false;	
	}
}
