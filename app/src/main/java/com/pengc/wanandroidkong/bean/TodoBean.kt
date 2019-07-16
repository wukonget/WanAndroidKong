package com.pengc.wanandroidkong.bean

import android.os.Parcel
import android.os.Parcelable

data class TodoBean(
    var date: Long,
    var todoList: List<TodoListBean>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.createTypedArrayList(TodoListBean.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(date)
        writeTypedList(todoList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TodoBean> = object : Parcelable.Creator<TodoBean> {
            override fun createFromParcel(source: Parcel): TodoBean = TodoBean(source)
            override fun newArray(size: Int): Array<TodoBean?> = arrayOfNulls(size)
        }
    }
}


data class TodoListBean(
    var completeDate: Long = 0,
    var completeDateStr: String? = null,
    var content: String = "",
    var date: Long = 0,
    var dateStr: String = "",
    var id: Long = 0,
    var status: Int = 0,
    var priority:Int = 0,
    var title: String = "",
    var type: Int = 0,
    var userId: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readLong(),
        source.readString(),
        source.readLong(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(completeDate)
        writeString(completeDateStr)
        writeString(content)
        writeLong(date)
        writeString(dateStr)
        writeLong(id)
        writeInt(status)
        writeInt(priority)
        writeString(title)
        writeInt(type)
        writeInt(userId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TodoListBean> = object : Parcelable.Creator<TodoListBean> {
            override fun createFromParcel(source: Parcel): TodoListBean = TodoListBean(source)
            override fun newArray(size: Int): Array<TodoListBean?> = arrayOfNulls(size)
        }
    }
}

data class TodoRespSection(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<TodoBean>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        1 == source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.createTypedArrayList(TodoBean.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(curPage)
        writeInt(offset)
        writeInt((if (over) 1 else 0))
        writeInt(pageCount)
        writeInt(size)
        writeInt(total)
        writeTypedList(datas)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TodoRespSection> = object : Parcelable.Creator<TodoRespSection> {
            override fun createFromParcel(source: Parcel): TodoRespSection = TodoRespSection(source)
            override fun newArray(size: Int): Array<TodoRespSection?> = arrayOfNulls(size)
        }
    }
}

data class TodoResp(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<TodoListBean>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        1 == source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.createTypedArrayList(TodoListBean.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(curPage)
        writeInt(offset)
        writeInt((if (over) 1 else 0))
        writeInt(pageCount)
        writeInt(size)
        writeInt(total)
        writeTypedList(datas)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TodoResp> = object : Parcelable.Creator<TodoResp> {
            override fun createFromParcel(source: Parcel): TodoResp = TodoResp(source)
            override fun newArray(size: Int): Array<TodoResp?> = arrayOfNulls(size)
        }
    }
}

