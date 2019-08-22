page = require('webpage').create();
system = require('system');
var address;
if (system.args.length < 3 || system.args.length > 5) {
	phantom.exit();
} else {
	page.settings.loadImages = true; //加载图片
	page.settings.resourceTimeout = 10000;//超过10秒放弃加载
	adress = system.args[1];
	outputPath = system.args[2];
	page.open(adress, function(status) {
		if (status != "success") {
			phantom.exit();
		}
		var bb = page.evaluate(function() {
			window.scrollTo(0, 10000);
			return document.getElementsByTagName('html')[0].getBoundingClientRect();
		});
		//获取页面实际高度（此处是用来设置截图的参数）
		page.viewportSize = {
			width : 1920,
			height : bb.height
		};
		window.setTimeout(function() {
//			window.scrollTo(0, 0);
			//在本地生成截图  
			console.log(page.content);
			page.render(outputPath);
			phantom.exit();
		}, 10000);
	});
}
