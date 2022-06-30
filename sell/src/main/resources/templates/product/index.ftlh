<!DOCTYPE html>
<html lang="en">
<#include "../common/header.ftlh">
<body>
<div id="wrapper" class="toggled">
    <#-- 边栏sidebar -->
    <#include "../common/nav.ftlh">

    <#-- 主要内容 content -->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.productName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label >单价</label>
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice)!""}"/>
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.productStock)!""}"/>
                        </div>
                        <div class="form-group">
                            <label >描述</label>
                            <input type="text" class="form-control" name="productDescription" value="${(productInfo.productDescription)!""}"/>
                        </div>
                        <div class="form-group">
                            <label >图片</label>
                            <img width="250" height="250" src="${(productInfo.productIcon)!""}">
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.productIcon)!""}"/>
                        </div>
                        <div class="form-group">
                            <label >种类</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType = category.categoryType>
                                        <option value="${category.categoryType}" selected>${category.categoryName}</option>
                                    <#else >
                                        <option value="${category.categoryType}">${category.categoryName}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <input type="hidden" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

