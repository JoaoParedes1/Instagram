package com.joaoparedes.instagram.post.view

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.BaseFragment
import com.joaoparedes.instagram.common.base.DependencyInjector
import com.joaoparedes.instagram.databinding.FragmentCameraBinding
import com.joaoparedes.instagram.databinding.FragmentGalleryBinding
import com.joaoparedes.instagram.post.Post
import com.joaoparedes.instagram.post.presenter.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter
    private var adapter = PictureAdapter() { uri ->
        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galleryNested?.smoothScrollTo(0, 0)
        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.PostRepository(requireContext()))
    }

    override fun getMenu(): Int = R.menu.menu_send

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_send -> {
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectedUri()))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setupViews() {
        binding?.galleryRv?.layoutManager = GridLayoutManager(requireContext(),3)
        binding?.galleryRv?.adapter = adapter

        presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullPictures(pictures: List<Uri>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRv?.visibility = View.VISIBLE
        adapter.items = pictures
        adapter.notifyDataSetChanged()
        binding?.galleryImgSelected?.setImageURI(pictures.first())
        binding?.galleryNested?.smoothScrollTo(0, 0)
        presenter.selectUri(pictures.first())
    }

    override fun displayEmptyPicture() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRv?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}