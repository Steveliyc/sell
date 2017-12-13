package com.lyc.exc.Controller;

import com.lyc.exc.dataobject.ProductCategory;
import com.lyc.exc.dataobject.ProductInfo;
import com.lyc.exc.service.ProductCategoryService;
import com.lyc.exc.service.ProductInfoService;
import com.lyc.exc.utils.ResultVOUtil;
import com.lyc.exc.vo.ProductInfoVO;
import com.lyc.exc.vo.ProductVO;
import com.lyc.exc.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyc94 on 2017/12/10.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2.查询类目
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        for(ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.拼装数据
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOs = new ArrayList<>();
            productVO.setProductInfoVOList(productInfoVOs);
            for(ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType()==productCategory.getCategoryType()) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOs.add(productInfoVO);
                }
            }
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
