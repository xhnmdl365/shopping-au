package com.cscd.shoppingau.sqlprovider.product;

import com.cscd.shoppingau.utils.Tool;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description
 * @Author Anthony
 * @Date 1/03/2018 6:03 PM
 */
public class ProductSqlProvider {
	public String getProductListByKeyword(@Param("wholeWord") final String wholeWord,
										  @Param("keywords") final String[] keywords,
										  @Param("brandId") final String brandId,
										  @Param("categoryId") final String categoryId,
										  @Param("priceRangeFrom") final int priceRangeFrom,
										  @Param("priceRangeTo") final int priceRangeTo,
										  @Param("sortBy") final String sortBy) {


		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		if(Tool.isNotEmpty(wholeWord)) {
			// this situation is only have brandId or categoryId
			sql.append(wrapSubSelect(getBrandProductSql(brandId, categoryId), "A"));
			sql.append(" UNION ");
		}

		sql.append(wrapSubSelect(getProductSql(wholeWord, brandId, categoryId), "B"));

		if(keywords != null && keywords.length > 1 && keywords.length < 3) {
			// if keywords have more than one word
			sql.append(getProductSubSql(keywords, brandId, categoryId));
		}
		sql.append(" ) as wrap");
		if(priceRangeFrom != -1 && priceRangeTo !=-1) {
			sql.append(" WHERE wrap.price between ").append(priceRangeFrom).append(" AND ").append(priceRangeTo);
		}
		if(Tool.isNotEmpty(sortBy)) {
			sql.append(" ORDER BY wrap.").append(sortBy);
		}

		return sql.toString();
	}

	public String getBrandsByProducts(@Param("wholeWord") final String wholeWord,
									  @Param("keywords") final String[] keywords,
									  @Param("categoryId") final String categoryId,
									  @Param("priceRangeFrom") final int priceRangeFrom,
									  @Param("priceRangeTo") final int priceRangeTo,
									  @Param("sortBy") final String sortBy) {

		final String brandId = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT brand.brand_id, count( brand.brand_id) num ,brand.name, brand.img_path from (");
		sql.append(getProductListByKeyword(wholeWord, keywords, brandId, categoryId, priceRangeFrom, priceRangeTo, sortBy));
		sql.append(")AS PRODUCT_LIST, brand")
			.append(" where PRODUCT_LIST.brand_id = brand.brand_id AND brand.status='1'")
			.append(" GROUP BY brand_id")
			.append(" ORDER BY num desc");
		return sql.toString();
	}

	public String getCategoriesByProducts(@Param("wholeWord") final String wholeWord,
										  @Param("keywords") final String[] keywords,
										  @Param("brandId") final String brandId,
										  @Param("categoryId") final String categoryId,
										  @Param("priceRangeFrom") final int priceRangeFrom,
										  @Param("priceRangeTo") final int priceRangeTo,
										  @Param("sortBy") final String sortBy) {

		SQL sql = new SQL(){{
			SELECT("count(PRODUCT_LIST.category_id) num, cat.category_id, cat.name");
			FROM("(" + getProductListByKeyword(wholeWord, keywords, brandId, categoryId, priceRangeFrom, priceRangeTo, sortBy) + ")AS PRODUCT_LIST , vertical_category cat");
			WHERE("POSITION(cat.category_id IN PRODUCT_LIST.category_id) AND " +
					"CASE WHEN #{categoryId} is NULL then cat.category_id=cat.parent_id else cat.parent_id=#{categoryId} " +
					" AND cat.category_id <> cat.parent_id end");
			GROUP_BY("PRODUCT_LIST.brand_id");
			ORDER_BY("num DESC");
		}};

		return sql.toString();
	}


	private String productSqlColumns =
				"sku.product_id," +
				" MIN(sku.price) price," +
				" p.brand_id, p.category_id, p.name, pi.img_path, sku.sale_volume, pr.rate";


	private String baseProductTables = "sku, product_img pi, product p";

	private String baseProductConditions(String brandId, String categoryId) {
		String brandIdCondition = "";
		String categoryIdCondition = "";
		if(Tool.isNotEmpty(brandId)) {
			brandIdCondition = " AND P.brand_id = #{brandId}";
		}
		if(Tool.isNotEmpty(categoryId)) {
			categoryIdCondition = " AND POSITION(#{categoryId} IN p.category_id)";
		}
		return (
				" p.status = 1" +
				" AND sku.status = 1" +
				" AND pi.status = 1" +
				" AND sku.product_id = p.product_id" +
				" AND pi.product_id = p.product_id" +
				" AND pi.sort_order = 1" +
				" AND sku.stock > 0" +
				brandIdCondition +
				categoryIdCondition
				);
	}


	private String getProductSql(String wholeWord, String brandId, String categoryId){
		String wholeWordCondition = "";
		if(Tool.isNotEmpty(wholeWord)) {
			wholeWordCondition = "POSITION(#{wholeWord} in p.name) AND ";
		}
		String finalWholeWordCondition = wholeWordCondition;
		SQL producSql = new SQL(){{
			SELECT(productSqlColumns);
			FROM(baseProductTables);
			LEFT_OUTER_JOIN("product_rate pr ON p.product_id = pr.product_id");
			WHERE(finalWholeWordCondition + baseProductConditions(brandId, categoryId));
			GROUP_BY("p.product_id");
		}};
		return producSql.toString();
	}

	// whole word split to single word
	private String getProductSubSql(String[] keywords, String brandId, String categoryId) {
		StringBuffer sql = new StringBuffer();
		int i;
		for(i = 0; i< keywords.length; i++) {
			final int j = i;
			System.out.println(j);
			SQL producSubSql = new SQL(){{
				SELECT(productSqlColumns);
				FROM(baseProductTables);
				LEFT_OUTER_JOIN("product_rate pr ON p.product_id = pr.product_id");
				WHERE("POSITION(#{keywords["+j+"]} in p.name) AND" + baseProductConditions(brandId, categoryId));
				GROUP_BY("p.product_id");
			}};
			sql.append(" UNION ");
			sql.append(wrapSubSelect(producSubSql.toString(), keywords[i]+Integer.toString(i)));
		}
		return sql.toString();
	}
	private String wrapSubSelect(String table, String tabelNickName) {
		return new SQL(){{
			SELECT ("*");
			FROM("(" +  table + ") AS " + tabelNickName);
			WHERE("product_id IS NOT NULL");
		}}.toString();
	}
	private String getBrandProductSql(String brandId, String categoryId) {

		SQL brandProducSql = new SQL(){{
			SELECT(productSqlColumns);
			FROM("brand b, " + baseProductTables);
			LEFT_OUTER_JOIN("product_rate pr ON p.product_id = pr.product_id");
			WHERE("p.brand_id = b.brand_id" +
					" AND b.name = #{wholeWord} AND" +
					baseProductConditions(brandId, categoryId)
			);
			GROUP_BY("p.product_id");
		}};

		return brandProducSql.toString();
	}
}
