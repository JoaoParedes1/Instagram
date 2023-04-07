package com.joaoparedes.instagram.profile.view

import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.BaseFragment
import com.joaoparedes.instagram.common.base.DependencyInjector
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth
import com.joaoparedes.instagram.databinding.FragmentProfileBinding
import com.joaoparedes.instagram.profile.Profile
import com.joaoparedes.instagram.profile.presenter.ProfilePresenter

class ProfileFragment: BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
    ), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter
    private val adapter = PostAdapter()
    private var uuid: String? = null

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        uuid = arguments?.getString(KEY_USER_ID)

        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(),3)
        binding?.profileRv?.adapter = adapter
        binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)

        binding?.profileBtnEditProfile?.setOnClickListener {
            if (it.tag == true) {
                binding?.profileBtnEditProfile?.text = getString(R.string.follow)
                binding?.profileBtnEditProfile?.tag = false
                presenter.followUser(uuid, false)
            } else if (it.tag == false) {
                binding?.profileBtnEditProfile?.text = getString(R.string.unfollow)
                binding?.profileBtnEditProfile?.tag = true
                presenter.followUser(uuid, true)
            }
        }

        presenter.fetchUserProfile(uuid)
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<UserAuth, Boolean?>) {
        val (userAuth, following) = user
        binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
        binding?.profileTxtPostsCount?.text = userAuth.followingCount.toString()
        binding?.profileTxtPostsCount?.text = userAuth.followersCount.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        binding?.profileImgIcon?.setImageURI(userAuth.photoUri)

        binding?.profileBtnEditProfile?.text = when(following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.profileBtnEditProfile?.tag = following

        presenter.fetchUserPosts(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPosts() {
        binding?.profileTxtEmpty?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.profileTxtEmpty?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_profile_grid -> {
                binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
            }
            R.id.menu_profile_list -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}
