package com.kisssum.smartcity.state.film

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FilmModel(application: Application) : AndroidViewModel(application) {
    private val popularData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val upcomingData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val sessionFilmPosition = MutableLiveData<Int>()
    private val upcomingFavoriteData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val commentData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val surroundingTheaterData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val sessionData = MutableLiveData<ArrayList<Map<String, String>>>()

    fun getPopularData() = popularData
    fun getUpcomingData() = upcomingData
    fun getSessionFilmPosition() = sessionFilmPosition
    fun getUpcomingFavoriteData() = upcomingData
    fun getCommentData() = commentData
    fun getSurroundingTheaterData() = surroundingTheaterData
    fun getSessionData() = sessionData

    private fun resetPopularData() {
        popularData.value = ArrayList()
        popularData.value?.apply {
            val map1 = HashMap<String, String>().apply {
                this["name"] = "我的姐姐"
                this["nameEnglish"] = "Sister"
                this["type"] = "剧情/家庭"
                this["upDate"] = "2021-04-02 18:00中国大陆上映"
                this["introduction"] = "一场意外车祸把父母带走，也把素未谋面的亲弟弟带到姐姐的面前。在一系列风波过后，姐姐原本来自原生家庭的伤痛慢慢被治愈，她也成长为更好的自己。"
                this["score"] = "8.9"
                this["scoreSize"] = "332719"
                this["wantSeeSize"] = "482535"
                this["seenSize"] = "19058246"
                this["duration"] = "127"
                this["playType"] = "国语2D"
            }

            val map2 = HashMap<String, String>().apply {
                this["name"] = "哥斯拉大战金刚"
                this["nameEnglish"] = "Godzilla vs Kong"
                this["type"] = "动作/冒险/科幻"
                this["upDate"] = "2021-03-26 08:00中国大陆上映"
                this["introduction"] = "影片中，这两位宛如神祇一般强大的对手于一场壮观的战争中相遇，彼时世界命运正悬于一线。为了找到真正的家园，金刚与他的保护者们踏上了一次艰难的旅程。与他们一道前行的还有一个年轻的孤儿女孩——吉雅，这个女孩与金刚之间存在着一种独特而强大的紧密联系。但意想不到的是，他们在前行的航道上与愤怒的哥斯拉狭路相逢，也由此在全球引起了一系列破坏。一股无形的力量造成了这两只巨兽之间的巨大冲突，深藏在地心深处的奥秘也由此揭开。"
                this["score"] = "8.9"
                this["scoreSize"] = "383503"
                this["wantSeeSize"] = "506799"
                this["seenSize"] = "29379229"
                this["duration"] = "113"
                this["playType"] = "国语2D"
            }

            val map3 = HashMap<String, String>().apply {
                this["name"] = "你好，李焕英"
                this["nameEnglish"] = "Hi,Mom"
                this["type"] = "喜剧/剧情"
                this["upDate"] = "2021-02-12 08:00中国大陆上映"
                this["introduction"] = "2001年的某一天，刚刚考上大学的贾晓玲(贾玲饰)经历了人生中的一次大起大落。一心想要成为母亲骄傲的她却因母亲突遭严重意外，而悲痛万分。在贾晓玲情绪崩溃的状态下，竟意外的回到了1981年，并与年轻的母亲李焕英(张小斐饰)相遇，二人形影不离，宛如闺蜜。与此同时，也结识了一群天真善良的好朋友。晓玲以为来到了这片“广阔天地”，她可以凭借自己超前的思维，让母亲“大有作为”，但结果却让晓玲感到意外.…"
                this["score"] = "9.5"
                this["scoreSize"] = "2362674"
                this["wantSeeSize"] = "1162302"
                this["seenSize"] = "120706398"
                this["duration"] = "128"
                this["playType"] = "国语2D"
            }

            for (i in 0..6) {
                this.add(map1)
                this.add(map2)
                this.add(map3)
            }
        }
    }

    private fun resetUpcomingData() {
        upcomingData.value = ArrayList()
        upcomingData.value?.apply {
            val map1 = HashMap<String, String>().apply {
                this["name"] = "追虎擒龙"
                this["nameEnglish"] = "Chasing the tiger and catching the dragon "
                this["type"] = "犯罪/剧情/动作"
                this["upDate"] = "2021-05-01中国大陆上映"
                this["introduction"] = "故事讲述 1973 年香港警黑勾结， 在英国人指使下贪污巨利，茶毒市民。港督成立廉政公署，最先两位检察官为黑白双煞(古天乐、林家栋)历尽千辛万苦调查权倾黑白两道的总华探长徐乐(吴镇宇)及跛豪(梁家辉)，终把二人绳之以法的故事。"
                this["score"] = "0.0"
                this["scoreSize"] = "0"
                this["wantSeeSize"] = "48979"
                this["seenSize"] = "0"
                this["duration"] = "0"
                this["playType"] = "国语2D"
            }

            val map2 = HashMap<String, String>().apply {
                this["name"] = "再见，少年"
                this["nameEnglish"] = "FAREWELL, MY LAD"
                this["type"] = "青春/剧情"
                this["upDate"] = "2021-05中国大陆上映"
                this["introduction"] = "影片讲述了千禧年发生在南方小镇上的一段曾经无限接近，却又渐行渐远的少年情谊。“好学生”黎菲与“坏孩子”张辰浩，各自经历了时代大潮下家庭的变迁，一起奋力而坚韧地成长。然而高考前的一场剧变，让青春笼上阴影，最终裹挟住了二人的命运。"
                this["score"] = "0.0"
                this["scoreSize"] = "0"
                this["wantSeeSize"] = "80486"
                this["seenSize"] = "0"
                this["duration"] = "105"
                this["playType"] = "国语2D"
            }

            val map3 = HashMap<String, String>().apply {
                this["name"] = "你的婚礼"
                this["nameEnglish"] = "My Love"
                this["type"] = "爱情"
                this["upDate"] = "2021-04-30中国大陆上映"
                this["introduction"] = "那个陪你穿校服的人，最后陪你走到了婚礼吗？电影《你的婚礼》讲述游泳特长生周潇齐与转校生尤咏慈长达15年的爱情故事。高中时，周潇齐(许光汉饰)对尤咏慈(章若楠饰)一见钟情，年少懵懂的纯纯爱恋，男孩默默守护，但女孩却不告而别。此后的人生，15年的爱情长跑。你的婚礼，也是我的成人礼。"
                this["score"] = "0.0"
                this["scoreSize"] = "0"
                this["wantSeeSize"] = "723616"
                this["seenSize"] = "0"
                this["duration"] = "0"
                this["playType"] = "国语2D"
            }

            for (i in 0..6) {
                this.add(map1)
                this.add(map2)
                this.add(map3)
            }
        }
    }

    private fun resetCommentData() {
        commentData.value = ArrayList()
        commentData.value?.apply {
            val map1 = HashMap<String, String>().apply {
                this["userName"] = "上官明超"
                this["userScore"] = "10"
                this["commentText"] = "来了来了，真的来了，下周6电影院不见不散！"
                this["commentTime"] = "2021-04-07"
                this["likes"] = "1898"
            }

            val map2 = HashMap<String, String>().apply {
                this["userName"] = "是茉阿"
                this["userScore"] = "6"
                this["commentText"] = "以前看过韩版的 两人高中相识相知 一直陪伴着对方 曾经在一起过 但最终男主还是看着女主成为了他人的新娘 出现在她的婚礼上 他们都曾后悔过 但在婚礼上微笑着祝福对方时 我觉得他们放下了 可能还是有会些意难平 记得那句话：相濡以沫 不如相忘于江湖。"
                this["commentTime"] = "2020-08-28"
                this["likes"] = "663"
            }

            val map3 = HashMap<String, String>().apply {
                this["userName"] = "vPB555***6583"
                this["userScore"] = "10"
                this["commentText"] = "反转真的很令我惊讶，电影比小品多的这个反转的情节真的是最催泪的一部分(个人认为)之前的剧情就会以为是贾玲在尽自己一切努力帮助母亲能够弥补曾经的遗憾事实上，母亲自始至终都是最明白的一个人李焕英正好被贾玲砸中，是因为掉下来的是她的女儿··…"
                this["commentTime"] = "2021-02-16"
                this["likes"] = "3011"
            }

            for (i in 0..6) {
                this.add(map1)
                this.add(map2)
                this.add(map3)
            }
        }
    }

    private fun resetSurroundingTheaterData() {
        surroundingTheaterData.value = ArrayList()
        surroundingTheaterData.value?.apply {
            val map = HashMap<String, String>().apply {
                this["name"] = "嘉麦数字影院(高塘店)"
                this["address"] = "北仑区富春江路236号(七彩虹广场三层)"
                this["star"] = "3"
                this["price"] = "35"
            }

            for (i in 0..20)
                this.add(map)
        }
    }

    private fun resetUpcomingFavoriteData() {
        upcomingFavoriteData.value = ArrayList()
    }

    private fun resetSessionData() {
        sessionData.value = ArrayList()
        sessionData.value?.apply {
            val map1 = HashMap<String, String>().apply {
                this["startTime"] = "16:25"
                this["endTime"] = "18:12"
                this["fileType"] = "剧情/家庭"
                this["playType"] = "国语2D"
                this["price"] = "34.9"
            }

            for (i in 0..18) {
                this.add(map1)
            }
        }
    }

    init {
        resetPopularData()
        resetUpcomingData()
        resetCommentData()
        resetSurroundingTheaterData()
        resetUpcomingFavoriteData()
        resetSessionData()
    }
}