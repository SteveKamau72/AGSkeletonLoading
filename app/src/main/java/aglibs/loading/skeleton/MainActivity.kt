package aglibs.loading.skeleton

import aglibs.loading.skeleton.drawer.ISkeletonDrawer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var loading = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startLoadingButton.setOnClickListener {
            loading(skeletonLoadingContainer, loading)
            loading = !loading
        }

//        listView.adapter = MyListAdapter(this, R.layout.item_list, arrayListOf("a", "b", "c", "d", "e", "f", "g", "h"))

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter =
//            RecyclerViewAdapter(this, arrayListOf("a", "b", "c", "d", "e", "f", "g", "h"))
    }


    inner class RecyclerViewAdapter(val context: Context, val items: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_list,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
    }

    inner class MyListAdapter(context: Context, resource: Int, objects: MutableList<String>) :
        ArrayAdapter<String>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        }
    }

    private fun loading(view: View, loading: Boolean) {
        view.takeIf { view is ViewGroup }?.let {
            for (i in 0 until (view as ViewGroup).childCount) {
                val childAt = view.getChildAt(i)
                (childAt as? ISkeletonDrawer)?.let {
                    if (!loading) {
                        it.startLoading()
                    } else {
                        it.stopLoading()
                    }
                }
                run { loading(childAt, loading) }
            }
        }

    }
}
