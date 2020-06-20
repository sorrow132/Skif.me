package yuresko.skifme.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import yuresko.skifme.R
import yuresko.skifme.adapter.ItemTouchCallback
import yuresko.skifme.adapter.RecyclerAdapter


class ActivityMenu : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView
//    private lateinit var contacts: ImageView

    private var adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        button = findViewById(R.id.buttonSaveMenu)
        recyclerView = findViewById(R.id.recyclerView)

//        contacts = findViewById(R.id.contacts)
//
//        contacts.setOnClickListener {
//            val i = Intent(Intent.ACTION_PICK)
//            i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
//            startActivityForResult(i, 0)
//        }

        adapter.submitList(adapter.listOfItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        initSwipe()

        val touchCallback: ItemTouchHelper.Callback = object : ItemTouchCallback(adapter) {
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(recyclerView)

        button.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(applicationContext)
                .inflate(
                    R.layout.bottom_sheet, findViewById(
                        R.id.bottom_sheet_container
                    )
                )
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }

    private fun initSwipe() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.submitList(
                    adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
                )
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.listOfItems.removeAt(viewHolder.adapterPosition)
            }
        })
    }
}