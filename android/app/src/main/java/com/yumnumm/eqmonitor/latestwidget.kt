package com.yumnumm.eqmonitor

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.*
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetPlugin

@SuppressLint("NewApi")
class latestwidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

@SuppressLint("NewApi")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val prefs:SharedPreferences = HomeWidgetPlugin.getData(context)
    // 最新情報を取得
    val max_intensity :String? = prefs.getString("max_intensity","?")
    val place :String? = prefs.getString("place","太陽")
    val magnitude: String? = prefs.getString("magnitude","M0.0")
    val time :String? = prefs.getString("time","2022/03/13 15:00")
    val imageNum:Int
    // image読み込み
    when(max_intensity){
        "0" -> imageNum = R.drawable.int0
        "1" -> imageNum = R.drawable.int1
        "2" -> imageNum = R.drawable.int2
        "3" -> imageNum = R.drawable.int3
        "4" -> imageNum = R.drawable.int4
        "5弱" -> imageNum = R.drawable.int5m
        "5強" -> imageNum = R.drawable.int5p
        "6弱" -> imageNum = R.drawable.int6m
        "6強" -> imageNum = R.drawable.int6p
        "7" -> imageNum = R.drawable.int7
        else -> imageNum = R.drawable.unknown
    }
    val views = RemoteViews(context.packageName, R.layout.latestwidget)
    // 更新
    views.setImageViewResource(R.id.intensityImageView,imageNum)
    /*views.setImageViewBitmap(R.id.intensityImageView, getCroppedBitmap(BitmapFactory.decodeResource(
        Resources.getSystem(),imageNum,null
    )))*/
    views.setTextViewText(R.id.dateTextView,time)
    //! views.setTextViewText(R.id.dateTextView,"dbg!")
    views.setTextViewText(R.id.placeText,place)
    views.setTextViewText(R.id.magunitudeText,magnitude)
    views.setImageViewResource(R.id.placeIcon,R.drawable.ic_baseline_location_on_24)
    views.setImageViewResource(R.id.magunitudeIcon,R.drawable.ic_baseline_double_arrow_24)
    views.setImageViewResource(R.id.timeicon,R.drawable.ic_baseline_access_time_filled_24)
    //views.setInt(R.id.notification_background,"setBackgroundResource", Color.parseColor("ff00000000"))

    // 角丸


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


fun getCroppedBitmap(bitmap: Bitmap): Bitmap? {
    val width: Int = bitmap.getWidth()
    val height: Int = bitmap.getHeight()
    val rect = Rect(0, 0, width, height)
    val rectf = RectF(0F, 0F, width.toFloat(), height.toFloat())
    val output: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    paint.setAntiAlias(true)
    canvas.drawRoundRect(rectf, (width / 5).toFloat(), (height / 5).toFloat(), paint)
    paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output
}
