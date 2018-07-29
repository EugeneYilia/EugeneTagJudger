// ==UserScript==
// @name            EugeneLiu
// @icon            https://d32kak7w9u5ewj.cloudfront.net/media/image/2017/10/382f873c678a418c9f8f3ce8a2485378.jpg
// @version         2.1
// @include         *
// @copyright       2018, AC
// @description     自动推荐智能的标签
// @author          刘一辰
// @note            2018.4.7-00:17-V1.1 第一版本
// @grant           none
// @require         http://code.jquery.com/jquery-1.11.0.min.js
// ==/UserScript==
window.onload = function () {
    if (document.body.clientWidth > 700) {
        document.body.innerHTML += "<input type='text' style='position: fixed;margin: 0 45%;top: 50px;text-align: center;color: red;z-index:99999;font-size: 17px;width:  80px;' id='EugeneInput'/>";
        document.body.innerHTML += '<a style="position: fixed;margin: 0 45%;top: 80px;text-align: center;color: darkolivegreen;z-index:99999;font-size: 17px;width:  80px;" href="mailto:583076221@qq.com?Subject=插件问题反馈&Body=网页的url为XX,出问题的超链接为XX,出现的问题情况为XX\r\n如果你能附上出问题的截图，我将对你的贡献万分感激                              Yours,EugeneLiu">反馈问题</a>';
        document.body.innerHTML += "<input type='button' style='position: fixed;margin: 0 44.5%;top: 110px;text-align: center;color: green;z-index:99999;font-size: 17px;width: 100px;background-color:white' id='closeWindow' value='关闭预浏览'/>";
        document.body.innerHTML += '<div id="iframe1" style="position: fixed;right: 0;top: 500px;z-index:5;">' +
            '<iframe id="iframe" style="border: 0px"></iframe>' +
            '</div>';
        $('#iframe1').hide();
        $('#iframe').hide();
        $("a").hover(function () {
                document.getElementById('EugeneInput').value = '';
                console.log("开始请求数据");
                var linkHref = $(this).attr('href');
                var protocal = "";
                if (linkHref.indexOf("https") === 0 || linkHref.indexOf("http") === 0) {
                    //linkHref为全链接
                } else if (linkHref.indexOf("//") == 0) {
                    protocal = window.location.protocol;
                    linkHref = protocal + linkHref;//linkHref为全链接
                }
                else {
                    protocal = window.location.protocol;
                    var domain = window.location.host;
                    var completeUrl = domain + linkHref;
                    linkHref = protocal + "//" + completeUrl;//linkHref为全链接
                }
                $('#iframe').attr('src', linkHref);
                $('#iframe').show();
                $('#iframe1').show();
                console.log('linkHref->' + linkHref);
                $.ajax(
                    {
                        //url: "http://localhost/requestTag",              //本地服务器的接口
                        url: "https://www.eugeneliu.top/EugeneContainer.server/requestTag",//服务器的接口
                        type: "POST",
                        data: {"requestUrl": linkHref},
                        success: function (result) {
                            console.log('result->' + result);
                            result = JSON.parse(result);
                            var recommendedTag = result.tag;
                            console.log("originalTag->" + recommendedTag);
                            recommendedTag = decodeURIComponent(recommendedTag);
                            console.log('decodedRecommendedTag->' + recommendedTag);
                            document.getElementById('EugeneInput').value = recommendedTag;
                        },
                        error: function (result) {
                            console.log(result);
                        }
                    });
            }, function () {
                console.log('mouse out');
            }
        );
        $('#closeWindow').click(function () {
           $('#iframe').hide();
           $('#iframe1').hide();
        });
    }
};