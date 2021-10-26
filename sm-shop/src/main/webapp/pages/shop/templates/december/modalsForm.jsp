<div class="modal fade" id="modalContactForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
 <form method="POST" modelAttribute="contact" action="/shop/store/priceEnquiry
 ">
 <div class="modal-dialog" role="document">
  <div class="modal-content">
   <div class="modal-header text-center">
    <h4 class="modal-title w-100 font-weight-bold">Ask For Price</h4>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
     <span aria-hidden="true">&times;</span>
    </button>
   </div>
   <div class="modal-body mx-3">
    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="name">Name</label>
     <input type="text" id="name" name="name" class="form-control validate" required>
    </div>

    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="quantity">Required Quantity</label>
     <input type="number" min="1" class="form-control validate"  value="1" name="quantity" id="quantity" required>
    </div>

    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="sku">Sku</label>
     <input type="text" id="sku" name="sku" class="form-control validate" required readonly>
    </div>

    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="mob">Mobile No.</label>
     <input type="text" pattern="[6-9]{1}[0-9]{9}" id="mob" name="phone" class="form-control validate" required>
    </div>

    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="emailId">Email</label>
     <input type="email" id="emailId" name="email" class="form-control validate" required>
    </div>

    <div class="md-form mb-5">
     <label data-error="wrong" data-success="right" for="city">City (Optional)</label>
     <input type="text" id="city" name="city" class="form-control validate">
    </div>

    <div class="md-form">
     <label data-error="wrong" data-success="right" for="des">Description (Optional)</label>
     <textarea type="text" id="des" name="description" class="md-textarea form-control" rows="4"></textarea>
    </div>

   </div>
   <div class="modal-footer d-flex justify-content-center">
    <button type="submit" class="btn btn-primary">Submit</button>
   </div>
  </div>
 </div>
 </form>
</div>

<script>
 $(document).on("click", ".open-askForPrice", function () {
  var sku = $(this).data('sku');
  $(".modal-body #sku").val( sku );
 });
</script>
