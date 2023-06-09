package com.github.anrimian.musicplayer.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.anrimian.musicplayer.R
import com.github.anrimian.musicplayer.databinding.FragmentAboutBinding
import com.github.anrimian.musicplayer.di.Components
import com.github.anrimian.musicplayer.ui.common.toolbar.AdvancedToolbar
import com.github.anrimian.musicplayer.ui.utils.ViewUtils
import com.github.anrimian.musicplayer.ui.utils.fragments.navigation.FragmentLayerListener
import com.github.anrimian.musicplayer.ui.utils.getAppInfo
import com.github.anrimian.musicplayer.ui.utils.slidr.SlidrPanel
import com.github.anrimian.musicplayer.utils.logger.FileLog

class AboutAppFragment : Fragment(), FragmentLayerListener {
    
    private lateinit var viewBinding: FragmentAboutBinding
    
    private lateinit var fileLog: FileLog
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAboutBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val toolbar: AdvancedToolbar = requireActivity().findViewById(R.id.toolbar)

        val appComponent = Components.getAppComponent()
        fileLog = appComponent.fileLog()
        val appLogger = appComponent.appLogger()
        val loggerRepository = appComponent.loggerRepository()

        val isLogExists = fileLog.isFileExists
        setLogActionsVisibility(isLogExists)
        if (isLogExists) {
            viewBinding.tvLogInfo.text = getString(
                R.string.log_info_text,
                fileLog.fileSize / 1024
            )
        }
        appComponent.aboutTextBinder().bind(this, viewBinding.tvAbout)

        viewBinding.btnDelete.setOnClickListener { deleteLogFile() }
        viewBinding.btnView.setOnClickListener { appLogger.startViewLogScreen(requireActivity()) }
        viewBinding.btnSend.setOnClickListener { appLogger.startSendLogScreen(requireActivity()) }

        viewBinding.cbShowReportDialogOnStart.isChecked = loggerRepository.isReportDialogOnStartEnabled
        ViewUtils.onCheckChanged(viewBinding.cbShowReportDialogOnStart, loggerRepository::showReportDialogOnStart)

        SlidrPanel.simpleSwipeBack(viewBinding.containerView, this, toolbar::onStackFragmentSlided)
    }

    override fun onFragmentMovedOnTop() {
        requireActivity().findViewById<AdvancedToolbar>(R.id.toolbar).setup { config ->
            config.setTitle(R.string.app_name)
            val appInfo = requireContext().getAppInfo()
            config.setSubtitle(getString(
                R.string.version_template,
                appInfo.versionName,
                appInfo.versionCode
            ))
        }
    }

    private fun deleteLogFile() {
        fileLog.deleteLogFile()
        setLogActionsVisibility(false)
        Toast.makeText(requireContext(), R.string.log_file_deleted, Toast.LENGTH_SHORT).show()
    }

    private fun setLogActionsVisibility(isLogExists: Boolean) {
        val logActionsVisibility = if (isLogExists) View.VISIBLE else View.GONE
        viewBinding.logActionsContainer.visibility = logActionsVisibility
        viewBinding.tvLogInfo.visibility = logActionsVisibility
    }
}