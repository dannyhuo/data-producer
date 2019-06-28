package com.data.dataproducer.config;

import com.data.dataproducer.entity.AArea;
import com.data.dataproducer.entity.AProductCategory;
import com.data.dataproducer.entity.AWebPage;
import com.data.dataproducer.enums.CategoryLevelEnum;
import com.data.dataproducer.service.IAAreaService;
import com.data.dataproducer.service.IAProductCategoryService;
import com.data.dataproducer.util.ReadUtil;
import com.data.dataproducer.util.UCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @author danny
 * @date 2019/5/30 8:09 PM
 */
@Configuration
@Slf4j
public class ResourceConfig {

    @Autowired
    private IAAreaService iaAreaService;

    @Autowired
    private IAProductCategoryService iaProductCategoryService;

    private InputStream getResourceAsStream (String name) {
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }

    @Bean
    public List<AArea> areas () {

        //load from db
        List<AArea> areas = iaAreaService.list();

        if (null != areas && areas.size() > 0) {
            return areas;
        }

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/a_area.csv"), line -> {
            try {
                String[] arr = line.split(",");
                AArea.AAreaBuilder builder = AArea.builder();
                builder.areaId(Long.parseLong(arr[0]));
                builder.province(arr[1]);
                builder.city(arr[2]);
                builder.contry(arr[3]);
                builder.bm(Integer.parseInt(arr[4]));
                areas.add(builder.build());
                System.out.println(builder.build().toString());
            } catch (Exception e) {

            }

        });

        //save to db
        iaAreaService.saveBatch(areas);

        return areas;
    }

    /**
     * 姓
     * @return
     */
    @Bean
    public List<String> family () {

        //load from db
        List<String> families = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/family.csv"), line -> {
            String[] arr = line.split(",");
            for (String family : arr) {
                families.add(family);
            }
        });

        return families;
    }

    /**
     * 手机号码段
     * @return
     */
    @Bean
    public List<Integer> mobilePrefix () {

        //load from db
        List<Integer> mobilePrefix = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/mobile-prefix.csv"), line -> {
            String[] arr = line.split(",");
            for (String mobile : arr) {
                mobilePrefix.add(Integer.parseInt(mobile));
            }
        });

        return mobilePrefix;
    }


    /**
     * 手机号码段
     * @return
     */
    @Bean
    public List<String> emailSuffix () {

        //load from db
        List<String> emailSuffix = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/email-suffix.csv"), line -> {
            String[] arr = line.split(",");
            for (String email : arr) {
                emailSuffix.add(email);
            }
        });

        return emailSuffix;
    }


    /**
     * 职业
     * @return
     */
    @Bean
    public List<String> profession () {

        //load from db
        List<String> list = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/profession.csv"), line -> {
            String[] arr = line.split(",");
            for (String email : arr) {
                list.add(email);
            }
        });

        return list;
    }


    /**
     * 职业
     * @return
     */
    @Bean
    public List<String> hobby () {

        //load from db
        List<String> list = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/hobby.csv"), line -> {
            String[] arr = line.split(",");
            for (String email : arr) {
                list.add(email);
            }
        });

        return list;
    }


    /**
     * 浏览器
     * @return
     */
    @Bean
    public List<String> browser () {

        //load from db
        List<String> list = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/browser.csv"), line -> {
            String[] arr = line.split(",");
            for (String email : arr) {
                list.add(email);
            }
        });

        return list;
    }


    /**
     * 平台
     * @return
     */
    @Bean
    public List<String> platform () {
        List<String> list = new ArrayList<>();
        list.add("PC");
        list.add("APP");
        list.add("微信");
        list.add("小程序");
        list.add("H5");
        list.add("WAP");
        return list;
    }

    /**
     * 支付银行
     * @return
     */
    @Bean
    public List<String> payStyle () {
        //load from db
        List<String> list = new ArrayList<>();

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/paystyle.csv"), line -> {
            String[] arr = line.split(",");
            for (String tmp : arr) {
                list.add(tmp);
            }
        });

        return list;
    }


    /**
     * 产品类别表
     * @return
     */
    @Bean
    public List<AProductCategory> productCategory () {
        //load from db
        List<AProductCategory> result = iaProductCategoryService.list();
        if (null != result && result.size() > 0) {
            return result;
        }

        //not exists in db, read from file
        Map<String, Map<String, List<String>>> category = new HashMap<>();
        ReadUtil.read( getResourceAsStream("/static/category.csv"), line -> {
            //一级分类
            String[] arr = line.split(",");
            Map<String, List<String>> category1 = category.get(arr[0]);
            if (null == category1) {
                category1 = new HashMap<>();
                category.put(arr[0], category1);
            }

            //二级分类
            List<String> category2 = category1.get(arr[1]);
            if(null == category2) {
                category2 = new ArrayList<>();
                category1.put(arr[1], category2);
            }

            //三级分类
            if (!category2.contains(arr[2])) {
                category2.add(arr[2]);
            }
        });

        //将分类表，build为AProductCategory对象
        result = new ArrayList<>();
        int base = 1000;
        //一级分类
        Iterator<Map.Entry<String, Map<String, List<String>>>> iterLevel1 = category.entrySet().iterator();
        while (iterLevel1.hasNext()) {
            Map.Entry<String, Map<String, List<String>>> next = iterLevel1.next();
            AProductCategory.AProductCategoryBuilder c1 = AProductCategory.builder();
            c1.categoryCode(UCodeUtil.code36(++base));
            c1.categoryName(next.getKey());
            c1.categoryLevel(CategoryLevelEnum.LEVEL_ONE.value());
            result.add(c1.build());

            //二级分类
            Iterator<Map.Entry<String, List<String>>> iterLevel2 = next.getValue().entrySet().iterator();
            while (iterLevel2.hasNext()) {
                Map.Entry<String, List<String>> entry = iterLevel2.next();
                AProductCategory.AProductCategoryBuilder c2 = AProductCategory.builder();
                c2.categoryCode(c1.build().getCategoryCode() + UCodeUtil.code36(++base));
                c2.categoryName(entry.getKey());
                c2.categoryLevel(CategoryLevelEnum.LEVEL_TWO.value());
                result.add(c2.build());

                //三级分类list
                List<String> c3es = entry.getValue();
                for (String c : c3es) {
                    AProductCategory.AProductCategoryBuilder tmp = AProductCategory.builder();
                    tmp.categoryCode(c2.build().getCategoryCode() + UCodeUtil.code36(++base));
                    tmp.categoryName(c);
                    tmp.categoryLevel(CategoryLevelEnum.LEVEL_THREE.value());
                    result.add(tmp.build());
                }
            }
        }

        //将分类保存至category表
        iaProductCategoryService.saveBatch(result);

        return result;
    }


    /**
     * 网站页面
     * @return
     */
    @Bean
    public Map<Integer, AWebPage> webPages () {
        //load from db
        Map<Integer, AWebPage> map = new HashMap<>(20);

        //not exists in db, read from file
        ReadUtil.read( getResourceAsStream("/static/web-page.csv"), line -> {
            if (!StringUtils.isEmpty(line)) {
                String[] arr = line.split(",");
                AWebPage.AWebPageBuilder builder = AWebPage.builder();
                Integer pageId = Integer.parseInt(arr[0]);
                builder.pageType(pageId);
                builder.pageUrl(arr[1]);
                builder.pageName(arr[2]);
                map.put(pageId, builder.build());
            }
        });

        return map;
    }

}
