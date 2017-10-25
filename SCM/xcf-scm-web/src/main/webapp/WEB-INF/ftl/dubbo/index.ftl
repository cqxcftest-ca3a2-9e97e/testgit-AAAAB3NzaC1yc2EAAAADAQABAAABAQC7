<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <script src="${staticFileRoot}/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $(document).ready(function(){
            $(".nav li").click(function(){
                var type_li=$(this).attr("data-type");
                //$("[id*='list']").hide();
                //$("#"+type_li).show();

                $(".nav li").removeClass("did")
                $(this).addClass("did")
            });

        });
    </script>

</head>
<body >
<script src="${staticFileRoot}/js/lib/dubbo-client.js" type="text/javascript" charset="utf-8"></script>
<div class="content">
    <div class="model-hearder">
        <form id="zk-form" action="init" method="get">
            <div class="list-form">
                <label class="list-label"> zookeeper-host:</label>
                <div class="list-input">
                    <input id="zk-host" name="zkhost" value="${zkHost!'172.20.11.103'}" />
                </div>
            </div>
            <div class="list-form">
                <label class="list-label">  dubbo-group:</label>
                <div class="list-input">
                    <input id="dubbo-group" name="group" value="dubbo" />
                </div>
            </div>
            <input type="button" class="btn-reload" value="reload" style="display:none" />
        </form>
    </div>
    <ul class="nav clearfix" id="interface">
        <#--<li data="list_1 " class="did" >list1</li>-->
        <#if (data?exists)>
            <#list data as f>
                <li data="${f} " ><a href="#" onclick="getMethods('${f}')"> ${f}</a></li>
            </#list>
        </#if>
    </ul>
    <div class="content-right">
        <div id="list_1" style="border: 1px solid #DDDDDD;">
            <div class="row">
                <#--<div style="margin: 20px 20px 0px 20px;" class="clearfix">

                </div>-->

                <div class="attention">
                    <h1>方法列表</h1>
                    <div id="div-methods" class="methods clearfix">

                    </div>
                </div>
            </div>
            <div class="row">
                <div class="attention">
                    <h1>方法明细：</h1>
                    <h2>输入：</h2>
                    <div id="div-input-desc" class="div-methods-detail"></div>
                    <h2>输入明细：</h2>
                    <div id="div-input--detail-desc" class="div-methods-detail"></div>
                    <h2>输出：</h2>
                    <div id="div-output-desc" class="div-methods-detail"></div>
                </div>

            </div>
            <div class="row">
                <div class="attention clearfix">
                   <#-- <h1>输入：</h1>-->
                    <div class="row-list clearfix methods">
                        <div class="list-form">
                            <label class="list-label">接口名</label>
                            <div class="list-input">
                                <input id="cls-selected" type="input" />
                            </div>
                        </div>
                        <div class="list-form">
                            <label class="list-label">  方法名</label>
                            <div class="list-input">
                                <input id="mtd-selected" type="input" />
                            </div>
                        </div>
                        <div class="list-form">
                            <label class="list-label">  dubbo版本号</label>
                            <div class="list-input">
                                <input id="dubbo-version" type="input" value="1.0" />
                            </div>
                        </div>
                    </div>
                    <div class="row-list clearfix methods" style="margin-top: 20px;">
                        <input id="method-input" type="textArea" style="width: 500px;height: 150px;" />
                    </div>
                    <div>
                        输入说明：参数1;参数2;参数3
                        ,<br>例子：input = new String[]{"{\"propertyId\":-1000,\"accountId\":10,\"nodeSeq\":\"-1000.\"}"};
                            res = client.invoke(AccountFacade.class.getName(), "1.0", "getCommunityTree", input)
                        <br>输入:{"propertyId":-1000,"accountId":10,"nodeSeq":"-1000."}
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="attention clearfix">
                    <input type="button" class="btn-execute" value="方法调用" />
                </div>
            </div>
            <div class="row">
                <div class="search-result">
                    调用结果
                    <div <#--type="textArea"--> id="div-result-execute" <#--style="width: 600px;height: auto;"--> class="search-result">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
