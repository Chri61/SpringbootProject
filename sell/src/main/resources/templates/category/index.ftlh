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
                    <form role="form" method="post" action="/sell/seller/category/save">

                        <div class="form-group">
                            <label>父id</label>
                            <select name="categoryParentId" class="form-control">
                                <option value="0">0</option>
                                <#list categoryIdList as cid>
                                    <#if (category.categoryParentId)?? && category.categoryParentId = cid>
                                        <option value="${cid}" selected>${cid}</option>
                                    <#else >
                                        <option value="${cid}">${cid}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" class="form-control" name="categoryName"
                                   value="${(category.categoryName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input type="number" class="form-control" name="categoryType"
                                   value="${(category.categoryType)!""}"/>
                        </div>
                        <input type="hidden" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

