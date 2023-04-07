package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "item")
@ToString
@Entity
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    // 상품 코드
    private Long id;

    @Column(nullable = false, length = 50)
    // 상품명
    private String itemNm;

    @Column(name = "price", nullable = false)
    // 가격
    private int price;

    @Column(nullable = false)
    // 재고수량
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    // 상품 상세 설명
    private String itemDetail;

    // 상품 판매 상태
    private ItemSellStatus itemSellStatus;

    // 등록 시간
//    private LocalDateTime regTime;

    // 수정 시간
//    private LocalDateTime updateTime;

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다. 현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
