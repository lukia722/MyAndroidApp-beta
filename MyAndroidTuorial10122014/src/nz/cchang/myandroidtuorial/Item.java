package nz.cchang.myandroidtuorial;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

public class Item implements Serializable {

	// �s���B����ɶ��B�C��B���D�B���e�B�ɮצW�١B�g�n���B�ק�B�w���
	private long id;
	private long datetime;
	private Colors color;
	private String title;
	private String content;
	private String fileName;
	private double latutude;
	private double longtude;
	private long lastModify;
	private boolean selected;

	public Item() {
		title = "";
		content = "";
		color = Colors.LIGHTGREY;
	}

	public Item(long id, long datetime, Colors color, String title,
			String content, String fileName, double latutude, double longtude,
			long lastModify) {
		this.id = id;
		this.datetime = datetime;
		this.color = color;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.latutude = latutude;
		this.longtude = longtude;
		this.lastModify = lastModify;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDatetime() {
		return datetime;
	}

	// �˸m�ϰ쪺����ɶ�
	public String getLocalDatetime() {
		return String.format(Locale.getDefault(), "%tF  %<tR", new Date(
				datetime));
	}

	// �˸m�ϰ쪺���
	public String getLocalDate() {
		return String.format(Locale.getDefault(), "%tF", new Date(datetime));
	}

	// �˸m�ϰ쪺�ɶ�
	public String getLocalTime() {
		return String.format(Locale.getDefault(), "%tR", new Date(datetime));
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getLatutude() {
		return latutude;
	}

	public void setLatutude(double latutude) {
		this.latutude = latutude;
	}

	public double getLongtude() {
		return longtude;
	}

	public void setLongtude(double longtude) {
		this.longtude = longtude;
	}

	public long getLastModify() {
		return lastModify;
	}

	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
