Android 二进制AndroidManifest.xml 文件解析器，提供了很弱的编辑功能，可用来编辑Application中Meta元素据的Value。



用法：
    java -jar AXMLPrinter2.jar    source_andoridManifest.xml -dir  output_dir  MetaKey  MetaValue

例如修改Umeng的渠道:
    java -jar AXMLPrinter2.jar    source_andoridManifest.xml -dir  output_dir  "UMENG_CHANNEL"  "umeng"


修改说明：
1、原作者的程序，只修改UMENG_CHANNEL这个key；现在扩展了一下，可以修改其它的key;
2、原来命令行是变参，可以传入多个渠道参数，现在限定5个参数，MetaKey,MetaValue同时传入；

	