package tao.deepbaytech.com.dayupicturesearch.entity;

/**
 * Created by EtherealPatrick on 2017/4/11.
 */

public class ImgMultipleItem {
  public static final int             FLAG = 1;
  public static final int             WARE = 2;
  public static final int             BLANK = 3;
  public static final int             SEARCH = 4;
  public static final int             TRANS = 5;
  public static final int             WARE_SPAN_SIZE = 2;
  public static final int             FLAG_SPAN_SIZE = 1;
  public static final int             BLANK_SPAN_SIZE = 1;
  public static final int             SEARCH_SPAN_SIZE = 4;
  public static final int             TRANS_SPAN_SIZE = 4;
  private             int             itemType;
  private             int             spanSize;
  private             String          flag;
  private             ProductItemBean bean;

  public ImgMultipleItem(int itemType, int spanSize, ProductItemBean bean) {
    this.itemType = itemType;
    this.spanSize = spanSize;
    this.bean = bean;
  }

  public ImgMultipleItem(int itemType, int spanSize, String flag) {
    this.itemType = itemType;
    this.spanSize = spanSize;
    this.flag = flag;
  }

  public int getItemType() {
    return itemType;
  }

  public void setItemType(int itemType) {
    this.itemType = itemType;
  }

  public int getSpanSize() {
    return spanSize;
  }

  public void setSpanSize(int spanSize) {
    this.spanSize = spanSize;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public ProductItemBean getBean() {
    return bean;
  }

  public void setBean(ProductItemBean bean) {
    this.bean = bean;
  }
}
