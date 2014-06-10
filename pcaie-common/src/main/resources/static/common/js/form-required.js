$(function(){
    $('.form-group').on('change', '.form-control', function(){
        var $this = $(this);
        var $formGroup = $this.parents('.form-group');
        if($this.val() == '') {
            $formGroup.removeClass('required-success');
            var $helpBlockDanger = $formGroup.find('.help-block-danger');
            if($helpBlockDanger.length > 0) {
                $formGroup.addClass('has-error');
                $helpBlockDanger.show();
            };
        } else {
        	if(!$formGroup.hasClass('has-error')) {
        		$formGroup.addClass('required-success');
        	}
//          $formGroup.addClass('required-success');
//            var $helpBlockDanger = $formGroup.find('.help-block-danger');
//            if($helpBlockDanger.length > 0) {
//                $formGroup.removeClass('has-error');
//                $helpBlockDanger.hide();
//            };
        }
    });
    $('.form-group .form-control').trigger('change');
});