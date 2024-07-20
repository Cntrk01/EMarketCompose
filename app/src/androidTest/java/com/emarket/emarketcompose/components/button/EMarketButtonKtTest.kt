package com.emarket.emarketcompose.components.button

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emarket.emarketcompose.ui.theme.EMarketComposeTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EMarketButtonKtTest {

    val buttonText = "Add to Cart"
    var isClicked = false

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonTextDisplayedWithCorrectText() {

        composeTestRule.setContent {
            EMarketComposeTheme {
                EMarketButton(text = buttonText, clickButton = {})
            }
        }

        composeTestRule.onNodeWithText(buttonText).assertIsDisplayed()
    }

    @Test
    fun buttonClickTest(){

        composeTestRule.setContent {
            EMarketComposeTheme {
                EMarketButton(
                    text = buttonText,
                    clickButton = {}
                )
            }
        }
        //Basit click işlemi için performClick() yeterli
        composeTestRule.onNodeWithText(buttonText).performClick()
        //isClicked değerini check eder .
        //Bir ifadenin doğru olup olmadığını kontrol eder. Eğer ifade false dönerse, bir AssertionError fırlatır. dilersek {} açıp içine optional mesaj yazabiliriz
        assert(isClicked)
    }

    @Test
    fun EMarketButton() {
        isClicked = false

        composeTestRule.setContent {
            EMarketComposeTheme {
                EMarketButton(
                    text = "Button",
                    clickButton = { isClicked = true}
                )
            }
        }
        //composeTestRule.onNodeWithText("Button").assertDoesNotExist()

        //Compose'un tüm işlemlerinin tamamlanmasını bekler. Bu, uzun süren işlemler veya animasyonlar gibi bekleme gerektiren durumlar için kullanışlıdır.
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Button").performClick()
        // UI thread'de belirtilen işlemleri gerçekleştirir.
        // UI thread'de çalıştırılır ve thread-safe işlemler yapmak için uygundur.
        // Bu, özellikle UI durumunu değiştiren veya UI ile doğrudan etkileşimde bulunan işlemler için önemlidir.
        composeTestRule.runOnIdle {
            assert(isClicked) //Burda da false değer geldiğiinde assertionFailed hatası verir . click değerini false tutunca veriyor
            //assertTrue() içine verdiğin boolean değer true olduğunda yanlızca true değerini döndürür.
        }

        //Örneğin onNodeWithText kullanıyoruz text kısımına yazdığımız isim birden çok yerde kullanılıyor . Bu durumdan kurtulmak için
        //val node = hasText("3") and hasClickAction()
        //composeTestRule.onNode(node).performClick() //dersek eğer yanlızca tıklanabilir bir 3 textli varsa performClick() kullanabiliriz.
    }

    //composeTestRule.onNodeWithText("1").assertDoesNotExist() Kullanıcı arayüzünde belirtilen metni ("1") içeren herhangi bir bileşenin olmadığını kontrol eder. Eğer bu metni içeren bir bileşen mevcutsa, test başarısız olur.

    //  @Test
    //    fun testCounterReset() {
    //        composeTestRule.setContent {
    //            // Counter composable'ı burada tanımlayın
    //            CounterScreen()
    //        }
    //
    //        // Sayacı artıran bir butona tıklayın
    //        composeTestRule.onNodeWithText("Increment").performClick()
    //        composeTestRule.onNodeWithText("1").assertExists() // "1" değeri var mı kontrol et
    //
    //        // Sayacı sıfırlayan bir butona tıklayın
    //        composeTestRule.onNodeWithText("Reset").performClick()
    //
    //        // "1" değeri artık mevcut olmamalı
    //        composeTestRule.onNodeWithText("1").assertDoesNotExist()
    //    }
}