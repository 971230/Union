@echo off  

cd .

::::::������Ŀ::::::

set "BASE_URL=http://gz.iwhalecloud.com:6134/svn/wssmall/ecs"

echo %BASE_URL%��ʼ

::::::::::::::::::::::::::::::::::::����ģ��::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=wssmall_1.2.0"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_core/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


cd %PROJECT_NAME%

::::::::::::::::::::::::::::::::::::����base::::::::::::::::::::::::::::::::::::
cd base_module\base\src\main

set "PROJECT_NAME=zj_inf"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_base/1.2.0/zj_inf/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����

::::::::::::::::::::::::::::::::::::order_server::::::::::::::::::::::::::::::::::::
cd ../../../../

set "PROJECT_NAME=order_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_order/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::crawler_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=crawler_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_crawler/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::esearch_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=esearch_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_esearch/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::goods_plugins_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=goods_plugins_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_goods/goods_plugins_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::goods_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=goods_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_goods/goods_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::info_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=info_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_info/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::member_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=member_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_member/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::num_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=num_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_num/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::orderctn_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderctn_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_orderctn/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::orderstd_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderstd_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_orderstd/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::orderecc_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderecc_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_orderecc/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::orderexp_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=orderexp_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_orderexp/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����

::::::::::::::::::::::::::::::::::::partner_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=partner_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_partner/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::payment_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=payment_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_payment/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::print_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=print_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_print/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::report_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=report_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_report/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::sms_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=sms_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_sms/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::supplier_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=supplier_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_supplier/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::mgWeb::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWeb"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_mgWeb/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::zj_goods_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=zj_goods_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_goods_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::zj_order_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=zj_order_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_order_server/1.2.0"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����

::::::::::::::::::::::::::::::::::::ecshandle_server::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=ecshandle_server"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/ecshandle_server/1.2.0/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


cd mgWeb\src\main\webapp

::::::::::::::::::::::::::::::::::::ecs_ord::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=ecs_ord"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_order_web/1.2.0/ecs_ord/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::mgWebthemesECSORD::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWebthemesECSORD"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_order_web/1.2.0/mgWebthemesECSORD/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


::::::::::::::::::::::::::::::::::::mgWebthemesECS::::::::::::::::::::::::::::::::::::

set "PROJECT_NAME=mgWebthemesECS"
echo checkout%PROJECT_NAME%��ʼ

set "PROJECT_PATH=wssmall_local/zj/zj_goods_web/1.2.0/mgWebthemesECS/"
set "URL=%BASE_URL%/%PROJECT_PATH%"
md %PROJECT_NAME%
echo %PROJECT_NAME% Ŀ¼�������
TortoiseProc.exe /command:checkout /closeonend:1 /noquestion /path:%PROJECT_NAME% /url:%URL% /closeonend:1

echo checkout %PROJECT_NAME% ����


@echo on