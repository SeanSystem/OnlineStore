package com.hyb.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 生成验证码图片
 * @author Sean
 *
 */
@Controller
public class CodeController {

	private final int WIDTH = 120;
	private final int HEIGHT = 33;

	@RequestMapping("/code")
	public void getCode(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		// 制作一张图片，指定图片的宽度、高度和类型，这种图片会被加载到内存中
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		// 得到图片的画笔
		Graphics g = image.getGraphics();
		// 1.设置背景色
		setBackgroudColor(g);
		// 2.设置图片的边框
		setBorder(g);
		// 3.写随机数
		String randomString = drawRandomNum((Graphics2D) g);
		HttpSession session = request.getSession();
		session.setAttribute("code", randomString);
		// 4.画干扰线
		drawRandomLine(g);
		// 5.图形写给浏览器
		// 给浏览器发送头，告诉浏览器以图片的方法打开
		response.setContentType("image/jpeg");
		// 将图片写给response对象，进而返回给浏览器
		ImageIO.write(image, "jpg", response.getOutputStream());

	}

	/**
	 * 设置背景
	 * @param g
	 */
	private void setBackgroudColor(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	/**
	 * 设置边框
	 * @param g
	 */
	private void setBorder(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}

	/**
	 * 画干扰线
	 * @param g
	 */
	private void drawRandomLine(Graphics g) {
		g.setColor(Color.GREEN);

		for (int i = 0; i < 5; i++) {

			int x1 = new Random().nextInt(120);
			int y1 = new Random().nextInt(35);
			int x2 = new Random().nextInt(120);
			int y2 = new Random().nextInt(35);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * 生成验证码文字
	 * @param g
	 * @return
	 */
	private String drawRandomNum(Graphics2D g) {
		// 注意\\u7684表示一个unicode字符
		/*String base = "\u7684\u4e00\u662f\u4e86\u6211\u4e0d\u4eba\u5728\u4ed6\u6709\u8fd9\u4e2a\u4e0a\u4eec\u6765"
				+ "\u5230\u65f6\u5927\u5730\u4e3a\u5b50\u4e2d\u4f60\u8bf4\u751f\u56fd\u5e74\u7740\u5c31\u90a3"
				+ ""
				+

				"\u548c\u8981\u5979\u51fa\u4e5f\u5f97\u91cc\u540e\u81ea\u4ee5\u4f1a\u5bb6\u53ef\u4e0b\u800c";*/
		String base = "1234567890abcdefghijklmnopqretuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		g.setColor(Color.red);
		g.setFont(new Font("宋体", Font.BOLD, 20));
		int x = 5;
		String string = "";
		for (int i = 0; i < 4; i++) {

			int range = new Random().nextInt() % 40;
			String ch = base.charAt(new Random().nextInt(base.length())) + "";
			string += ch;
			// totate方法可以设置内容的旋转弧度
			g.rotate(range * Math.PI / 180, x, 30);
			g.drawString(ch, x, 30);
			// 清除旋转弧度，防止下次的旋转角度太大
			g.rotate(-range * Math.PI / 180, x, 30);
			x += 30;
		}

		return string;
	}

}
