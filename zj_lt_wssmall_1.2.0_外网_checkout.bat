@echo off  

cd .

::::::下载项目::::::

set "BASE_URL=http://gz.iwhalecloud.com:6134/svn/wssmall/ecs"

echo %BASE_URL%开始

::::::::::::::::::::::::::::::::::::基础模块::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=wssmall_1.2.0"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_core/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


cd %PROJECT_NAME%

::::::::::::::::::::::::::::::::::::本地base::::::::::::::::::::::::::::::::::::
cd base_module\base\src\main

set "PROJECT_NAME=zj_inf"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_base/1.2.0/zj_inf/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束

::::::::::::::::::::::::::::::::::::order_server::::::::::::::::::::::::::::::::::::
cd ../../../../

set "PROJECT_NAME=order_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_order/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::crawler_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=crawler_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_crawler/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::esearch_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=esearch_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_esearch/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::goods_plugins_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=goods_plugins_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_goods/goods_plugins_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::goods_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=goods_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_goods/goods_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::info_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=info_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_info/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::member_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=member_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_member/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::num_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=num_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_num/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::orderctn_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderctn_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_orderctn/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::orderstd_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderstd_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_orderstd/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::orderecc_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderecc_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_orderecc/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::orderexp_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderexp_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_orderexp/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束

::::::::::::::::::::::::::::::::::::partner_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=partner_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_partner/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::payment_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=payment_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_payment/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::print_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=print_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_print/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::report_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=report_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_report/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::sms_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=sms_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_sms/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::supplier_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=supplier_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_supplier/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::mgWeb::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWeb"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_mgWeb/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::zj_goods_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=zj_goods_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_goods_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::zj_order_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=zj_order_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_order_server/1.2.0"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束

::::::::::::::::::::::::::::::::::::ecshandle_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=ecshandle_server"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/ecshandle_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


cd mgWeb\src\main\webapp

::::::::::::::::::::::::::::::::::::ecs_ord::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=ecs_ord"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_order_web/1.2.0/ecs_ord/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::mgWebthemesECSORD::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWebthemesECSORD"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_order_web/1.2.0/mgWebthemesECSORD/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


::::::::::::::::::::::::::::::::::::mgWebthemesECS::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWebthemesECS"
echo checkout%PROJECT_NAME%开始

set "PROJECT_PATH=wssmall_local/zj/zj_goods_web/1.2.0/mgWebthemesECS/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% 目录创建完成
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% 结束


@echo on