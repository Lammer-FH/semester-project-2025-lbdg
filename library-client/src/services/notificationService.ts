import { toastController } from '@ionic/vue';
import { closeOutline } from 'ionicons/icons';

export async function notify(
    message: string,
    type: 'success' | 'danger' | 'warning' | 'primary' = 'primary'
) {
    const toast = await toastController.create({
        message,
        duration: 5000,
        color: type,
        position: 'bottom',
        cssClass: 'custom-toast',
        buttons: [
            {
                icon: closeOutline,
                role: 'cancel',
                handler: () => {
                    console.log('Toast manually dismissed');
                },
            },
        ],
    });
    await toast.present();
}