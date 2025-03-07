import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.ContactAdapter2
import com.example.contactapp.ContactHelper
import com.example.contactapp.R

class ListFragment2 : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter2

    private val CONTACT_PERMISSION_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        checkPermissionAndLoadContacts()

        return view
    }

    private fun checkPermissionAndLoadContacts() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), CONTACT_PERMISSION_CODE)
        } else {
            loadContacts()
        }
    }

    private fun loadContacts() {
        val contacts = ContactHelper.getContacts(requireContext())
        adapter = ContactAdapter2(contacts)
        recyclerView.adapter = adapter
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                Toast.makeText(requireContext(), "Разрешение на доступ к контактам отклонено", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
