# springboot-file-upload-download
项目中常会遇到需要文件上传和下载的地方，其中文件上传的安全问题不可忽视，下面是我考虑到的文件上传安全问题和用编码实现的过程  - 对文件大小的判断，防止恶意上传大文件挤占服务器资源 - 对文件类型的判断，这里实现的是对图片的判断 - 对图片进行resize处理，防止图片嵌入恶意可执行的代码，通过压缩可以实现对嵌入可代码的破坏 - 文件保存的地址有两种，一个就是借助第三方服务器进行保存（七牛云），或者是放在自己的服务器，在成功读取文件后，进行保存的时候，可以对文件名进行修改，采用随机数，一定程度上提高了安全性 - 文件服务器和应用服务器的分开，避免对应用程序的直接破坏 - 文件夹权限的设置，对用户上传的文件夹设置只读权限，可以有效防止远端直接启动木马程序 - 当然，假如这个功能不需要，直接关闭文件上传功能是最安全的。
详情请见本人博客：
https://luffy997.github.io/
