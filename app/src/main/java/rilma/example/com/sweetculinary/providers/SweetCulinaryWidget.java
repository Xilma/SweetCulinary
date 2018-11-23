package rilma.example.com.sweetculinary.providers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.utils.ConstantValues;
import rilma.example.com.sweetculinary.utils.ServiceWidget;
import rilma.example.com.sweetculinary.views.DetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class SweetCulinaryWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, String jsonRecipeIngredients, int imgResId, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sweet_culinary_widget);

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ConstantValues.WIDGET_EXTRA, "FROM_WIDGET");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (jsonRecipeIngredients.equals("")) {
            jsonRecipeIngredients = "No ingredients!";
        }

        views.setTextViewText(R.id.widget_ingredients, jsonRecipeIngredients);
        views.setImageViewResource(R.id.widget_icon, imgResId);

        // OnClick intent for textview
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // Gets called once created and on every update period
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ServiceWidget.startActionOpenRecipe(context);
    }

    public static void updateWidgetRecipe(Context context, String jsonRecipe, int imgResId, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        // Update all widgets
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, jsonRecipe, imgResId, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}