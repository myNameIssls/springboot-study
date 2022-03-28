<html>

<head>
    <style>
        span{
            border-bottom: 1px solid black;
        }
        p{
            line-height: 1.5;
        }
        @page {
            size: 210mm 297mm; /*设置纸张大小:A4(210mm 297mm)、A3(297mm 420mm) 横向则反过来*/
            margin-bottom: 1cm;

            padding: 1em;

            @top-center {
                content: "页眉中间位置";
                font-family: SimSun;
                font-size: 15px;
                color:#000;
            };

            @bottom-center{
                content: "页脚中间位置";
                font-family: SimSun;
                font-size: 15px;
                color:#000;
            };

            @bottom-right{
                content: "第" counter(page) "页 共" counter(pages) "页";
                font-family: SimSun;
                font-size: 15px;
                color:#000;
            };
        }

        #pagenumber:before {
            content: counter(page);
        }

        #pagecount:before {
            content: counter(pages);
        }

    </style>
</head>

<body style="font-family: SimSun; ">
    <p>
        <h1 style="text-align: center;">公司授权委托书</h1>
    </p>
    <br /><br />
    <p>致：<span>${tenEntName!}</span></p>
    <p>&#160;&#160;&#160;&#160;我单位现委托<span>${userName!}</span>作为我单位合法委托代理人，授权其代表我单位进行<span>${indexTitle!}</span>账户相关管理工作。该委托代理人的授权范围为:代表我单位在<span>${indexTitle!}</span>上注册、签署文件、使用<span>${indexTitle!}</span>、资金交易、融资等与<span>${indexTitle!}</span>有关的一切事务。在整个<span>${indexTitle!}</span>使用过程中，该代理人的一切行为，均代表本单位，与本单位的行为具有同等法律效力。本单位将承担该代理人行为带来的全部法律后果和法律责任。</p>
    <p>&#160;&#160;&#160;&#160;以上授权委托有效期自盖章之日（含当日）起至<span>${indexTitle!}</span>账户注销和/或<span>${indexTitle!}</span>全部融资款项结算完毕终止。</p>
    <p>&#160;&#160;&#160;&#160;代理人无权转换代理权。</p>
    <p>&#160;&#160;&#160;&#160;特此委托。</p>
    <p>&#160;&#160;&#160;&#160;代理人姓名:<span>${userName!}</span></p>
    <p>&#160;&#160;&#160;&#160;身份证号码:<span>${userNum!}</span></p>
    <br /><br />
    <p>&#160;&#160;&#160;&#160;委托人(盖章):</p>
    <p>&#160;&#160;&#160;&#160;日期:<span>${year!}</span>年<span>${month!}</span>月<span>${day!}</span>日</p>
    <br /><br />    <br /><br />    <br /><br />    <br /><br />    <br /><br />    <br /><br />
    <br /><br />    <br /><br />    <br /><br />
    <p>附:委托代理人身份证复印件（<span>正反面、</span>加盖公章）</p>

    <div id = "header"></div>

</body>

</html>