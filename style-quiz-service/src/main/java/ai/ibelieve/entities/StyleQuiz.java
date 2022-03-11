package ai.ibelieve.entities;

import java.util.HashMap;
import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class StyleQuiz {

    private String styleQuizId;
    private String userId;
    private String gender;
    private List<String> occasion;
    private List<String> dressingStyle;
    private List<String> weather;
    private List<String> weatherCondition;
    private int height;
    private int bust;
    private int waist;
    private int hip;
    private int inseam;
    private String bodyType;
    private String underTone;
    private String skinTone;
    private List<String> brands;
    private List<String> topStyle;
    private List<String> neckStyle;
    private List<String> sleeveStyle;
    private List<String> pantStyle;
    private List<String> skirtLength;
    private List<String> dressType;
    private List<String> shoeType;
    private List<String> colorPalatte;
    private List<String> patternStyle;
    private List<String> fabricAllergies;
    private List<String> socialMedia;

    public StyleQuiz() {
    }

    @DynamoDbSortKey
    public String getStyleQuizId() {
        return styleQuizId;
    }
    public void setStyleQuizId(String styleQuizId) {
        this.styleQuizId = styleQuizId;
    }

    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = "UserId#"+userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getOccasion() {
        return occasion;
    }

    public void setOccasion(List<String> occasion) {
        this.occasion = occasion;
    }

    public List<String> getDressingStyle() {
        return dressingStyle;
    }

    public void setDressingStyle(List<String> dressingStyle) {
        this.dressingStyle = dressingStyle;
    }

    public List<String> getWeather() {
        return weather;
    }

    public void setWeather(List<String> weather) {
        this.weather = weather;
    }

    public List<String> getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(List<String> weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getInseam() {
        return inseam;
    }

    public void setInseam(int inseam) {
        this.inseam = inseam;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getUnderTone() {
        return underTone;
    }

    public void setUnderTone(String underTone) {
        this.underTone = underTone;
    }

    public String getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(String skinTone) {
        this.skinTone = skinTone;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public List<String> getTopStyle() {
        return topStyle;
    }

    public void setTopStyle(List<String> topStyle) {
        this.topStyle = topStyle;
    }

    public List<String> getNeckStyle() {
        return neckStyle;
    }

    public void setNeckStyle(List<String> neckStyle) {
        this.neckStyle = neckStyle;
    }

    public List<String> getSleeveStyle() {
        return sleeveStyle;
    }

    public void setSleeveStyle(List<String> sleeveStyle) {
        this.sleeveStyle = sleeveStyle;
    }

    public List<String> getPantStyle() {
        return pantStyle;
    }

    public void setPantStyle(List<String> pantStyle) {
        this.pantStyle = pantStyle;
    }

    public List<String> getSkirtLength() {
        return skirtLength;
    }

    public void setSkirtLength(List<String> skirtLength) {
        this.skirtLength = skirtLength;
    }

    public List<String> getDressType() {
        return dressType;
    }

    public void setDressType(List<String> dressType) {
        this.dressType = dressType;
    }

    public List<String> getShoeType() {
        return shoeType;
    }

    public void setShoeType(List<String> shoeType) {
        this.shoeType = shoeType;
    }

    public List<String> getColorPalatte() {
        return colorPalatte;
    }

    public void setColorPalatte(List<String> colorPalatte) {
        this.colorPalatte = colorPalatte;
    }

    public List<String> getPatternStyle() {
        return patternStyle;
    }

    public void setPatternStyle(List<String> patternStyle) {
        this.patternStyle = patternStyle;
    }

    public List<String> getFabricAllergies() {
        return fabricAllergies;
    }

    public void setFabricAllergies(List<String> fabricAllergies) {
        this.fabricAllergies = fabricAllergies;
    }

    public List<String> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<String> socialMedia) {
        this.socialMedia = socialMedia;
    }
}
