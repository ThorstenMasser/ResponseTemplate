Response Template
=================

This application is to manage canned responses in different languages. You can add your own ones and edit all existing ones as well.

How to use existing templates?
------------------------------
In the top part of the application you can choose your response type and the language. In main part, you will see a preview of this response. 
To use this templates, simply click on 'Use'. The system will now check if the text contains placeholders (words in square brackets like [name]) and ask you for the actual value you want to use. After giving the system all information, you will have the complete text available in your clipboard and you can use it in any application you want just by pasting it.
As you may have seen during this process, some of the values for the placeholders were already filled out. This is because of some default values defined in the system. You can define your own default values for each placeholder by clicking on 'Defaults'. In the new window, just enter the placeholder's name (without the square brackets) and give it a value. Next time you want to use a template where this placeholder is used, you will see your default value already filled out when the system asks you for it.

How to manage templates?
------------------------
You can easily create your own templates just by clicking on 'New'. Enter a response type, a language and the text itself. Of course, you can also use the placeholders mentioned in the previous section. Just use the square brackets and the system will behave in the same way as for the existing templates.
Also editing of existing templates s possible. You will see the same interface as you use to create new entries. Only the fields are already populated.
To remove a template entry you have two options. Either delete only one language for a request type (choose the language and click on 'Delete language entry') or you can delete the complete response type with all it's languages (by clicking on 'Delete complete entry').

What is the 'Settings' button for?
----------------------------------
Currently there is only one option and this option ('Data Folder') is to change the location of your XML-Files. The idea was at the beginning to use one common set of data files so everybody could benefit from new entries etc. But unfortunately, we currently don't have a shared folder where these files could be located. So at the moment, please keep it empty and put the xml files in the same folder as your .jar file

Summary of the buttons
----------------------
'Use' - Use the currently selected template (will be copied in your clipboard after setting all placeholders)
'Edit' - Edit the currently selected template
'New' - Create a new template
'Delete language entry' - Delete the currently selected language version of the template
'Delete complete entry' - Delete the currently selected template with all language versions
'Defaults' - Set the defaults for the placeholders
'Settings' - Settings of the application (ignore it for the moment)
'Exit' - Exit the application