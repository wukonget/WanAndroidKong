package com.pengc.wanandroidkong.utils

import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject

object StringUtil {
    /**
     * 格式化
     *
     * @param jsonStr
     * @return
     * @author lizhgb
     * @Date 2015-10-14 下午1:17:35
     * @Modified 2017-04-28 下午8:55:35
     */
    fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr)
            return ""
        val sb = StringBuilder()
        var last: Char
        var current = '\u0000'
        var indent = 0
        var isInQuotationMarks = false
        for (i in 0 until jsonStr.length) {
            last = current
            current = jsonStr[i]
            when (current) {
                '"' -> {
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks
                    }
                    sb.append(current)
                }
                '{', '[' -> {
                    sb.append(current)
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent++
                        addIndentBlank(sb, indent)
                    }
                }
                '}', ']' -> {
                    if (!isInQuotationMarks) {
                        sb.append('\n')
                        indent--
                        addIndentBlank(sb, indent)
                    }
                    sb.append(current)
                }
                ',' -> {
                    sb.append(current)
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }

        return sb.toString()
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    @Throws(JSONException::class)
    fun ifgetJson(reqjson: String) {
        try {
            JSONObject.parseObject(reqjson)
        } catch (e: JSONException) {
            throw JSONException("进件系统请求json串，不是正规json格式，请求json串为==$reqjson")

        }

    }
}
